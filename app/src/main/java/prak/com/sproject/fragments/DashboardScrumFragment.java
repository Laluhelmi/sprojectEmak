package prak.com.sproject.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
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

import prak.com.sproject.BeanClassForDetail;
import prak.com.sproject.BeanClassForListView;
import prak.com.sproject.BeanClassForTodo;
import prak.com.sproject.DBHelper;
import prak.com.sproject.DetailTodoAdapter;
import prak.com.sproject.MainActivity;
import prak.com.sproject.R;
import prak.com.sproject.AmbilProfile;
import prak.com.sproject.RestClient;
import prak.com.sproject.activity.FixedTab;
import prak.com.sproject.listViewAdapter;

/**
 * Created by Yoza on 24/08/2016.
 */

public class DashboardScrumFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    public DashboardScrumFragment() {
        // Required empty public constructor
    }

    ProgressDialog prg;
    RestClient rest;
    String idproject,titleproject,token;

    String[] usernametim,jabatantim;
    String[] judultodo,statustodo,estimasi;


    ArrayList<BeanClassForListView> data;
    listViewAdapter listViewAdapter;



    String[] listku,deskripsipro,listkunotif,deskripsinotif;

    ListView listViewpro,listviewnotif;



    TextView judul,error;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    MenuItem navho;
    Menu menu;
    DBHelper db;
    private SwipeRefreshLayout swipeRefreshLayout,swipeempty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.detailproject,container,false);
        prg=new ProgressDialog(getActivity());
        prg.setMessage("Please Wait");
        prg.setCancelable(false);
        listViewpro=(ListView)v.findViewById(R.id.listviewtodo);


        data=new ArrayList<BeanClassForListView>();
        rest=new RestClient(getActivity().getApplicationContext());

        error=(TextView)v.findViewById(R.id.error);



        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh_layout);
        swipeempty=(SwipeRefreshLayout)v.findViewById(R.id.swipeRefreshLayout_emptyView);

        db = new DBHelper(getActivity().getApplicationContext());

        if (db.CheckIsDataAlreadyInDBorNot()) {

            this.token = String.valueOf(db.getaLL());

        }


        //this.token=cobafrag.gettoken();

        //Toast.makeText(getActivity(), "INIID PRO D OEFRAGMENT"+idproject, Toast.LENGTH_SHORT).show();
        //tampiltodo(idproject);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeempty.setOnRefreshListener(this);

        listViewpro.setEmptyView(swipeempty);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                tampilProject(token);
            }
        });
        return v;
    }


    private void tampilProject(String token) {
        Log.e("ini link ",String.valueOf(RestClient.PROJECTLIST)+token);
        StringRequest projectlist=new StringRequest(Request.Method.GET,RestClient.PROJECTLIST+token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ini judul ", "cobajudul");


                try {
                    JSONObject dashobj = new JSONObject(response);
                    Boolean status = dashobj.getBoolean("status");

                    if (status) {
                        data.clear();
                        JSONArray arrayproject = dashobj.getJSONArray("pesan");

                        listku = new String[arrayproject.length()];
                        deskripsipro=new String[arrayproject.length()];
                        for (int i = 0; i < arrayproject.length(); i++) {
                            JSONObject detailobj = arrayproject.getJSONObject(i);

                            String idku=detailobj.getString("id_project");
                            String judulku = detailobj.getString("judul");
                            String deskripsi=detailobj.getString("deskripsi");


//                            listku[i] = judulku;
//                            deskripsipro[i]=deskripsi;

                            BeanClassForListView beanClass=new BeanClassForListView();
                            beanClass.setIdpro(idku);
                            beanClass.setTitle(judulku);
                            beanClass.setDescription(deskripsi);
                            data.add(beanClass);

                        }

                        prg.hide();

                        listViewAdapter=new listViewAdapter(getActivity(),data);

                        listViewpro.setAdapter(listViewAdapter);

                        listViewAdapter.notifyDataSetChanged();




                    }

                    else if(status==false) {

                        listViewpro.setVisibility(View.GONE);
                        error.setVisibility(View.VISIBLE);

                        swipeempty.setRefreshing(false);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeRefreshLayout.setRefreshing(false);

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
                    Toast.makeText(getActivity().getApplicationContext(), "cek koneksi internet anda", Toast.LENGTH_LONG).show();
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


        }; projectlist.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        Volley.newRequestQueue(getActivity().getApplicationContext()).add(projectlist);


    }


    @Override
    public void onRefresh() {

        tampilProject(DashboardScrumFragment.this.token);

    }
}
