package prak.com.sproject.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import prak.com.sproject.BeanClassForChatHistory;
import prak.com.sproject.BeanClassForListView;
import prak.com.sproject.BeanClassForNotif;
import prak.com.sproject.ChatHistoryAdapter;
import prak.com.sproject.DetailNotifAdapter;
import prak.com.sproject.RestClient;
import prak.com.sproject.R;
import prak.com.sproject.listViewAdapter;

/**
 * Created by Yoza on 29/08/2016.
 */

public class HistoryDaily extends AppCompatActivity {
    ProgressDialog prg;
    RestClient rest;
    TextView userku,firstku,lastku,emailku,register,notif;
    int id;
    private String tokenSent,myuser,idproject;
    private ImageView imageViewRound;

    ArrayList<BeanClassForListView> data;

    ArrayList<BeanClassForChatHistory> datahistorychat;

    DetailNotifAdapter notifAdapter;
    ChatHistoryAdapter chatHistoryAdapter;

    ArrayAdapter<String> adapter;

    String[] listku,deskripsipro,listkunotif,deskripsinotif;
    ListView listViewchathisto;
    Button editprofilbaru;
    private Toolbar mtoolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView,navigationViewleft;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chathistorylist);
        mtoolbar=(Toolbar)findViewById(R.id.toolbarhistorychat);
        setSupportActionBar(mtoolbar);

        if(getSupportActionBar()!=null) {
            Drawable drawable = getResources().getDrawable(R.drawable.menu);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        }

        prg=new ProgressDialog(this);
        prg.setMessage("Please Wait");
        prg.setCancelable(false);
        datahistorychat=new ArrayList<BeanClassForChatHistory>();

        listViewchathisto=(ListView)findViewById(R.id.listviewkuchathstory);


        Bundle extraschat=getIntent().getExtras();
        this.idproject=extraschat.getString("idprojecthisto");





        tampilhistory(idproject);


    }

    private void tampilhistory(final String idproject) {
        Log.e("ini link ",String.valueOf(RestClient.PROJECTLIST)+idproject);
        userku = (TextView) findViewById(R.id.username);
        StringRequest chathistolist=new StringRequest(Request.Method.GET,RestClient.CHATLIST+idproject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ini judul ", "cobajudul");

                try {
                    JSONObject dashobj = new JSONObject(response);
                    Boolean status = dashobj.getBoolean("status");

                    if (status) {
                        JSONArray arrayproject = dashobj.getJSONArray("pesan");

                        listku = new String[arrayproject.length()];
                        deskripsipro=new String[arrayproject.length()];
                        for (int i = 0; i < arrayproject.length(); i++) {
                            JSONObject detailobj = arrayproject.getJSONObject(i);

                            String tanggal=detailobj.getString("tanggal");

                            BeanClassForChatHistory beanChatClass=new BeanClassForChatHistory();
                            beanChatClass.setTanggalchat(tanggal);
                            beanChatClass.setIdprochat(HistoryDaily.this.idproject);

                         //   Toast.makeText(HistoryDaily.this, "Ini di chat history "+userku.getText(), Toast.LENGTH_SHORT).show();
                            datahistorychat.add(beanChatClass);

                        }

                        prg.hide();

                        chatHistoryAdapter=new ChatHistoryAdapter(HistoryDaily.this,datahistorychat);

                        listViewchathisto.setAdapter(chatHistoryAdapter);
                        //  listviewnotif.setAdapter(listViewAdapter);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prg.hide();
                NetworkResponse response=error.networkResponse;
                if(response!=null&&response.data!=null){
                    rest.statusCode(response.statusCode,response.data);
                }
                else{
                    Toast.makeText(getApplicationContext(), "cek koneksi internet anda", Toast.LENGTH_LONG).show();
                }

            }

        }

        )

        {
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
                Map<String,String> params=new HashMap<>();
//                String credentials = "PHASAR"+":"+"newstartupfromkampusuad";
//                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                String credentials = "PHASAR"+":"+"newstartupfromkampusuad";
                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                params.put("X-API-KEY",RestClient.X_API_KEY);
                return params;
            }

//            @Override
//            protected Map<String, String> getParams(){
//
//                Map<String, String> params=new HashMap<String, String>();
//               // params.put("username",uname.getText().toString());
//               // params.put("password",pwd.getText().toString());
//                return params;
//            }


        }; chathistolist.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        Volley.newRequestQueue(getApplicationContext()).add(chathistolist);


    }
}
