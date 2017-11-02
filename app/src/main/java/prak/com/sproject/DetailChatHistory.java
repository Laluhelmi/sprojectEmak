package prak.com.sproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

/**
 * Created by Yoza on 30/08/2016.
 */

public class DetailChatHistory extends AppCompatActivity {

    private RestClient rest;
    private Handler mhandler;
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatArrayAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    String idprochathisto,tanggalhisto;
    String nama,tokenchat;
    DBHelper db;

    ListView listtodo;
    TextView judul,error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detailprojectchathistory);

        Bundle extras=getIntent().getExtras();
        idprochathisto=extras.getString("idprosentchat");
        tanggalhisto=extras.getString("tanggalsent");
        SharedPreferences sp = this.getSharedPreferences("edu.upi.yudiwbs.dataku", Context.MODE_PRIVATE);
        this.nama = sp.getString("nama","");

       // Toast.makeText(this, "Id di detil histo ="+idprochathisto+" dan tanggal ="+tanggalhisto, Toast.LENGTH_SHORT).show();

        rest=new RestClient(getApplicationContext());

        messagesContainer = (ListView)findViewById(R.id.messagesContainerhisto);
        TextView tanggalhistonya=(TextView)findViewById(R.id.tanggalLbl);
        tanggalhistonya.setText(tanggalhisto.toString());

        loadDummyHistory();


       // messageET = (EditText)findViewById(R.id.messageEdit);
       // sendBtn = (Button)findViewById(R.id.chatSendButton);




    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);

    }

    private void loadDummyHistory(){
        //Context filecontext = null;

        chatHistory = new ArrayList<ChatMessage>();

        // tapung=tapung+Character.toString((char)c);



        StringRequest chat=new StringRequest(Request.Method.GET, "http://apiscrum.alfatech.id/index.php/app/read_chat_by_project_bydate/" + idprochathisto+"/"+tanggalhisto, new Response.Listener<String>() {
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

                            if(userchat.equals(DetailChatHistory.this.nama)){
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
                        adapter = new ChatArrayAdapter(DetailChatHistory.this, new ArrayList<ChatMessage>());
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
                    Toast.makeText(getApplicationContext(),"cek koneksi internet anda",Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(getApplicationContext()).add(chat);




    }
}
