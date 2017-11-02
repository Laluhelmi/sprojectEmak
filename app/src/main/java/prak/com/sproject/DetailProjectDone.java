package prak.com.sproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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

/**
 * Created by Yoza on 16/08/2016.
 */

public class DetailProjectDone extends AppCompatActivity {

    ProgressDialog prg;
    RestClient rest;
    String idproject,titleproject;
    String[] usernametim,jabatantim;
    ArrayList<BeanClassForDone> datadone;
    ArrayList<BeanClassForDetail> datadetail;
    DetailDoneAdapter detaildoneadapter;
    ListView listdone;
    TextView titleproo;
    TextView judul;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    MenuItem navho;
    Menu menu;
    LinearLayout todoku,doneku;
    DetailProjectAdapter objek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailproject_done);
        todoku=(LinearLayout)findViewById(R.id.todoku);
        doneku=(LinearLayout)findViewById(R.id.done);

        todoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),DetailProject.class);

                i.putExtra("idprosent",idproject);
                i.putExtra("titlesent",titleproject);
                startActivity(i);
            }
        });



        prg=new ProgressDialog(this);
        prg.setMessage("Please Wait");
        prg.setCancelable(false);
        listdone=(ListView)findViewById(R.id.listviewdone);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        //titleproo=(TextView)findViewById(R.id.usernamekoe);


        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.navigation);


        View header=navigationView.getHeaderView(0);
        judul=(TextView)header.findViewById(R.id.namaku);



        rest=new RestClient(getApplicationContext());
        datadetail=new ArrayList<BeanClassForDetail>();



        datadone=new ArrayList<BeanClassForDone>();
        Bundle extras=getIntent().getExtras();
        idproject=extras.getString("idprosent");
        titleproject=extras.getString("titlesent");

        judul.setText("Project Name : "+titleproject);
        //titleproo.setText("Project Name : "+titleproject);
        // objek.tampilanggota(idproject);

        menu=navigationView.getMenu();





        tampilanggota(idproject);
        //tampilanggota(idproject);


        tampildoing(idproject);

    }

    private void tampildoing(String idproject) {


        StringRequest todo=new StringRequest(Request.Method.GET, "http://servicescrum.16mb.com/app/show_done_sprint/" + idproject + "?X-API-KEY=tifuad", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject detailobjtoo = new JSONObject(response);
                    Boolean status = detailobjtoo.getBoolean("status");

                    Log.e("INI MASUK TAMPL",String.valueOf(status));

                    if (status) {

                        JSONArray detailtodo = detailobjtoo.getJSONArray("pesan");

                        Log.e("todo error", String.valueOf(detailtodo));
                        //judultodo = new String[detailtodo.length()];
                        //estimasi = new String[detailtodo.length()];
                        // statustodo = new String[detailtodo.length()];

                        for (int i = 0; i < detailtodo.length(); i++) {
                            JSONObject moredetailtodo = detailtodo.getJSONObject(i);

                            String judultimvar = moredetailtodo.getString("judul");
                            //String estimasitimvar = moredetailtodo.getString("estimasi");
                            // String statustodotimvar=moredetailtodo.getString("status");

                            BeanClassForDone beandetaildone = new BeanClassForDone();
                            beandetaildone.setJuduldone(judultimvar);
                            //beandetail.setJabatantimku(jabatantimvar);
                            //menu.add(i+1+"."+beandetail.getUsernametimku()+"("+beandetail.getJabatantimku()+")");

                            datadone.add(beandetaildone);


                        }


                        detaildoneadapter = new DetailDoneAdapter(DetailProjectDone.this, datadone);
                        //listtim.setAdapter(detailadapter);
                        listdone.setAdapter(detaildoneadapter);

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
                Map<String,String> params=new HashMap<>();
                params.put(RestClient.X_API_KEY,RestClient.KEY);
                return params;
            }


        }; todo.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplicationContext()).add(todo);

    }

    public void tampilanggota(String idproject) {


        StringRequest anggota=new StringRequest(Request.Method.GET, "http://servicescrum.16mb.com/app/show_tim_by_project/" + idproject + "?X-API-KEY=tifuad", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject detail = new JSONObject(response);
                    Boolean status = detail.getBoolean("status");

                    Log.e("INI MASUK TAMPL ANGGOTA",String.valueOf(status));

                    if (status) {

                        JSONArray detailpro = detail.getJSONArray("pesan");

//                        usernametim = new String[detailpro.length()];
//                        jabatantim = new String[detailpro.length()];

                        for (int i = 0; i < detailpro.length(); i++) {
                            JSONObject moredetail = detailpro.getJSONObject(i);

                            String usernametimvar = moredetail.getString("username");
                            String jabatantimvar = moredetail.getString("jabatan");

                            BeanClassForDetail beandetail = new BeanClassForDetail();
                            beandetail.setUsernametimku(usernametimvar);
                            beandetail.setJabatantimku(jabatantimvar);
                            menu.add(i+1+"."+beandetail.getUsernametimku()+"("+beandetail.getJabatantimku()+")");

                            datadetail.add(beandetail);


                        }


                        objek = new DetailProjectAdapter(DetailProjectDone.this, datadetail);
                        //listtim.setAdapter(detailadapter);

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
                Map<String,String> params=new HashMap<>();
                params.put(RestClient.X_API_KEY,RestClient.KEY);
                return params;
            }


        }; anggota.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplicationContext()).add(anggota);

    }
}
