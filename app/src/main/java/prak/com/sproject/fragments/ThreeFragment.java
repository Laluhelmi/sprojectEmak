package prak.com.sproject.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import prak.com.sproject.BeanClassForDetail;
import prak.com.sproject.BeanClassForTodo;
import prak.com.sproject.DetailTodoAdapter;
import prak.com.sproject.R;
import prak.com.sproject.RestClient;
import prak.com.sproject.activity.FixedTab;

/**
 * Created by Yoza on 18/08/2016.
 */

public class ThreeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public ThreeFragment() {
        // Required empty public constructor
    }

    ProgressDialog prg;
    RestClient rest;
    String idproject,titleproject;

    String[] usernametim,jabatantim;
    String[] judultodo,statustodo,estimasi;

    ArrayList<BeanClassForDetail> datadetail;
    ArrayList<BeanClassForTodo> datatodo;


    DetailTodoAdapter detailTodoAdapter;

    ListView listtodo;

    TextView judul,error;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    MenuItem navho;
    Menu menu;
    private SwipeRefreshLayout swipeRefreshLayout,swipeempty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentView v=inflater.inflate(R.layout.detailproject,container,false);
        View v=inflater.inflate(R.layout.detailproject,container,false);
        prg=new ProgressDialog(getActivity());
        prg.setMessage("Please Wait");
        prg.setCancelable(false);
        listtodo=(ListView)v.findViewById(R.id.listviewtodo);

        rest=new RestClient(getActivity().getApplicationContext());
        datatodo=new ArrayList<BeanClassForTodo>();
        error=(TextView)v.findViewById(R.id.error);

        FixedTab aktif=(FixedTab)getActivity();

        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh_layout);
        swipeempty=(SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshLayout_emptyView);



        this.idproject=aktif.getMydata();

        //Toast.makeText(getActivity(), "INIID PRO D OEFRAGMENT"+idproject, Toast.LENGTH_SHORT).show();
        //tampiltodo(idproject);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeempty.setOnRefreshListener(this);
        listtodo.setEmptyView(swipeempty);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                tampiltodo(idproject);
            }
        });
        return v;
    }
    private void tampiltodo(String idproject) {

        swipeRefreshLayout.setRefreshing(true);

        StringRequest todo=new StringRequest(Request.Method.GET, "http://apiscrum.alfatech.id/index.php/app/show_done_sprint/" + idproject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject detailobjtoo = new JSONObject(response);
                    Boolean status = detailobjtoo.getBoolean("status");

                    Log.e("INI MASUK TAMPL",String.valueOf(status));

                    if (status) {
                        datatodo.clear();

                        JSONArray detailtodo = detailobjtoo.getJSONArray("pesan");

                        Log.e("done error", String.valueOf(detailtodo));
                        //judultodo = new String[detailtodo.length()];
                        //estimasi = new String[detailtodo.length()];
                        // statustodo = new String[detailtodo.length()];

                        for (int i = 0; i < detailtodo.length(); i++) {
                            JSONObject moredetailtodo = detailtodo.getJSONObject(i);

                            String judultimvar = moredetailtodo.getString("task");
                            //String estimasitimvar = moredetailtodo.getString("estimasi");
                            // String statustodotimvar=moredetailtodo.getString("status");

                            BeanClassForTodo beandetailtodo = new BeanClassForTodo();
                            beandetailtodo.setJudultodo(judultimvar);
                            //beandetail.setJabatantimku(jabatantimvar);
                            //menu.add(i+1+"."+beandetail.getUsernametimku()+"("+beandetail.getJabatantimku()+")");

                            datatodo.add(beandetailtodo);


                        }


                        detailTodoAdapter = new DetailTodoAdapter(getActivity(), datatodo);
                        //listtim.setAdapter(detailadapter);
                        listtodo.setAdapter(detailTodoAdapter);
                        detailTodoAdapter.notifyDataSetChanged();

                        // swipeRefreshLayout.setRefreshing(false);
                    }
                    else if(status==false) {

                        listtodo.setVisibility(View.GONE);
                        error.setVisibility(View.VISIBLE);

                        swipeempty.setRefreshing(false);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                swipeRefreshLayout.setRefreshing(false);            }

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
                Map<String,String> params=new HashMap<>();
                String credentials = "PHASAR"+":"+"newstartupfromkampusuad";
                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                params.put("X-API-KEY",RestClient.X_API_KEY);
                return params;
            }


        }; todo.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(todo);

    }


    @Override
    public void onRefresh() {


        tampiltodo(ThreeFragment.this.idproject);


    }
}
