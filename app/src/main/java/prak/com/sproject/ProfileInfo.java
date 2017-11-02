package prak.com.sproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
 * Created by Reza on 4/13/2016.
 */
public class ProfileInfo extends Activity {

    ProgressDialog prg;
    RestClient rest;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    String[] coba;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileinfo);

        prg=new ProgressDialog(this);
        prg.setMessage("Tunggu ya.....");
        prg.setCancelable(false);

        listView=(ListView)findViewById(R.id.listprofile);
//        data=new ArrayList<>();

        profile();


       // listView.setOnItemClickListener(this);


    }



    private void profile(){

        StringRequest cekprofile=new StringRequest(Request.Method.GET, RestClient.API_PROFILE_CEK, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                prg.hide();

                try{
                    JSONObject obj=new JSONObject(response);
                    Boolean error=obj.getBoolean("error");



                    if(error==false){

                        JSONArray arrayku=obj.getJSONArray("data");

                        coba=new String[arrayku.length()];
                        for (int i=0; i<arrayku.length();i++){

                            JSONObject obj1= arrayku.getJSONObject(i);

                            String username=obj1.getString("username");
                            String name=obj1.getString("first_name");

                            coba[i]=username+","+name;



                            Log.e("PUNYAKU ERROR: ", coba[i].toString());

                        }


                        adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.item,coba);


                        listView.setAdapter(adapter);





                    }

                }catch (JSONException e){

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                prg.hide();
                NetworkResponse response=error.networkResponse;
                if(response!=null&&response.data!=null){

                    rest.statusCode(response.statusCode, response.data);
                    // rest.message("salah user atau pass");

                  //  hasil.setText("gagal loh");
                    //Toast.makeText(getApplicationContext(),"isgi, ini sukses",Toast.LENGTH_LONG).show();
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
                params.put(RestClient.X_API_KEY,RestClient.KEY);
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


        }; cekprofile.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplicationContext()).add(cekprofile);


    }


}
