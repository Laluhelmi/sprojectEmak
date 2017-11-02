package prak.com.sproject.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;


import prak.com.sproject.AmbilProfile;
import prak.com.sproject.ChatArrayAdapter;
import prak.com.sproject.ChatMessage;
import prak.com.sproject.DBHelper;
import prak.com.sproject.R;
import prak.com.sproject.RestClient;
import prak.com.sproject.activity.FixedTab;

/**
 * Created by Yoza on 22/08/2016.
 */

public class FourFragment extends Fragment{

    public FourFragment() {
    }

    private RestClient rest;
    private Handler mhandler;
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatArrayAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    String idproject,userchatprofku;
    String nama,tokenchat;
    DBHelper db;

    ListView listtodo;
    TextView judul,error;

    //private SwipeRefreshLayout swipeRefreshLayout,swipeempty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.detailprojectchat, container, false);
        initControls();

       // listtodo=(ListView)v.findViewById(R.id.listviewtodo);

        FixedTab aktif=(FixedTab)getActivity();
        this.idproject=aktif.getMydata();
        this.userchatprofku=aktif.getUsercht();

        rest=new RestClient(getActivity().getApplicationContext());


        //FileInputStream fin=filecontext.openFileInput("hello");
        //int c;


        //Toast.makeText(getActivity(), "file read"+tapung, Toast.LENGTH_SHORT).show();


        db=new DBHelper(getActivity().getApplicationContext());
        if (db.CheckIsDataAlreadyInDBorNot()) {

            String tok = String.valueOf(db.getaLL());
            this.tokenchat=tok;


        }

        mhandler=new Handler();


        return v;

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messagesContainer = (ListView)view.findViewById(R.id.messagesContainer);
        messageET = (EditText)view.findViewById(R.id.messageEdit);
        sendBtn = (Button) view.findViewById(R.id.chatSendButton);

        SharedPreferences sp = this.getActivity().getSharedPreferences("edu.upi.yudiwbs.dataku",Context.MODE_PRIVATE);
        this.nama = sp.getString("nama","");
        // int umur = sp.getInt("umur", 0);

//        AlertDialog ad = new AlertDialog.Builder(getActivity()).create();
//        ad.setMessage("nama:"+nama);
//        ad.show();

        TextView meLabel = (TextView)view.findViewById(R.id.meLbl);
        TextView companionLabel = (TextView)view.findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout)view.findViewById(R.id.container);
        companionLabel.setText("Scrum Team");



        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                StringRequest sendchat=new StringRequest(Request.Method.POST,"http://apiscrum.alfatech.id/index.php/app/kirim_chat/"+idproject+"/"+tokenchat, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject objsend = new JSONObject(response);
                            Boolean status = objsend.getBoolean("status");

                            if (status) {
                                String pesanchat = objsend.getString("pesan");
                                Toast.makeText(getActivity(), "" + String.valueOf(pesanchat), Toast.LENGTH_SHORT).show();


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // prg.hide();
                        NetworkResponse response=error.networkResponse;
                        if(response!=null&&response.data!=null){

                            rest.statusCode(response.statusCode, response.data);
                            // rest.message("salah user atau pass");

                            //  hasil.setText("gagal loh");
                            //Toast.makeText(getApplicationContext(),"isgi, ini sukses",Toast.LENGTH_LONG).show();
                        }
                        else{

                            Toast.makeText(getActivity().getApplicationContext(), "cek koneksi internet anda", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                ){

                    @Override
                    protected void onFinish() {
                        super.onFinish();

                        loadDummyHistory();

                    }

                    @Override
                    protected void deliverResponse(String response) {
                        super.deliverResponse(response);
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params=new HashMap<>();
                        String credentials = "PHASAR"+":"+"newstartupfromkampusuad";
                        String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        params.put("Authorization", auth);
                        params.put("X-API-KEY",RestClient.X_API_KEY);
                        return params;
                    }

                    @Override
                    protected Map<String, String> getParams(){

                        Map<String, String> params=new HashMap<String, String>();
                        // params.put("email", emailedit.getText().toString());
                        params.put("message",messageText);
                        //params.put("status", statusedit.getText().toString());
                        return params;
                    }

                };
                sendchat.setRetryPolicy(new DefaultRetryPolicy(
                        3000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                Volley.newRequestQueue(getActivity().getApplicationContext()).add(sendchat);

//                ChatMessage chatMessage = new ChatMessage();
//                chatMessage.setId(122);//dummy
//                chatMessage.setMessage(messageText);
//                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//                chatMessage.setMe(true);

                messageET.setText("");



                //displayMessage(chatMessage);
            }
        });

    }

    private void initControls() {






    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);

    }

    Runnable mAutoRefreshRunnable = new Runnable() {
        @Override
        public void run() {
            loadDummyHistory();
            mhandler.postDelayed(mAutoRefreshRunnable, 5000);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        mhandler.postDelayed(mAutoRefreshRunnable, 5000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mhandler.removeCallbacks(mAutoRefreshRunnable);
    }


    private void loadDummyHistory(){
        //Context filecontext = null;

        chatHistory = new ArrayList<ChatMessage>();

               // tapung=tapung+Character.toString((char)c);



        StringRequest chat=new StringRequest(Request.Method.GET, "http://apiscrum.alfatech.id/index.php/app/read_chat_by_project_bynow/" + idproject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject detailobjchat = new JSONObject(response);
                    Boolean statuschat = detailobjchat.getBoolean("status");

                    if (statuschat) {

                        JSONArray detailchat = detailobjchat.getJSONArray("pesan");


                        for (int i = 0; i < detailchat.length(); i++) {

                            JSONObject moredetailchat = detailchat.getJSONObject(i);

                            String isichat = moredetailchat.getString("message");
                            String userchat=moredetailchat.getString("username");

                            ChatMessage msg = new ChatMessage();

                            //Log.e("INI PERBANDINGAN",userchat+"="+userchatprofku);

                            if(userchat.equals(FourFragment.this.nama)){
                                msg.setId(i);
                                msg.setMe(true);
                                msg.setChatname(userchat);
                               // Toast.makeText(getActivity(), "user :"+userchat+":"+String.valueOf(msg.getIsme()), Toast.LENGTH_SHORT).show();

                                //Log.e("INI PERBANDINGAN",userchat+"="+String.valueOf(msg.getIsme()));
                                msg.setMessage(isichat);
                                msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                                chatHistory.add(msg);
                            }
                            else {
                                msg.setId(i);
                                msg.setMe(false);
                                msg.setChatname(userchat);
                                msg.setMessage(isichat);
                                msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                                chatHistory.add(msg);
                            }
                            //Log.d("NI STATUS",String.valueOf(msg.getIsme()));

                         //   Toast.makeText(getActivity(), "user "+userchatprofku+String.valueOf(msg.getIsme()), Toast.LENGTH_SHORT).show();





                        }
                        adapter = new ChatArrayAdapter(getActivity(), new ArrayList<ChatMessage>());
                        messagesContainer.setAdapter(adapter);

                        for(int i=0; i<chatHistory.size(); i++) {
                            ChatMessage message = chatHistory.get(i);
                            displayMessage(message);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response=error.networkResponse;
                if(response!=null&&response.data!=null){
                    rest.statusCode(response.statusCode,response.data);
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"cek koneksi internet anda",Toast.LENGTH_LONG).show();
                }
            }
        }
        ){
            @Override
            protected void onFinish() {
                super.onFinish();
            }

            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> header=new HashMap<>();
                String credentials = "PHASAR"+":"+"newstartupfromkampusuad";
                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                header.put("Authorization", auth);
                header.put("X-API-KEY",RestClient.X_API_KEY);
                return header;
            }
        }; chat.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(chat);


//        ChatMessage msg = new ChatMessage();
//        msg.setId(1);
//        msg.setMe(false);
//        msg.setMessage("Hi");
//        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        chatHistory.add(msg);
//
//        ChatMessage msg1 = new ChatMessage();
//        msg1.setId(2);
//        msg1.setMe(false);
//        msg1.setMessage("How r u doing???");
//        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        chatHistory.add(msg1);
//
//        adapter = new ChatArrayAdapter(getActivity(), new ArrayList<ChatMessage>());
//        messagesContainer.setAdapter(adapter);

//        for(int i=0; i<chatHistory.size(); i++) {
//            ChatMessage message = chatHistory.get(i);
//            displayMessage(message);
//        }

    }



}
