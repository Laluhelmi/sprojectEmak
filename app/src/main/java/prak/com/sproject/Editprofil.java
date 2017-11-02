package prak.com.sproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Reza on 4/15/2016.
 */
public class Editprofil extends Activity {

    String id_edit;
    RestClient rest;
    ProgressDialog prg;

    EditText emailedit,firstedit,lastedit,displayedit,statusedit,usernameditvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofilbaru);
        prg=new ProgressDialog(this);
        prg.setMessage("Tunggu ya.....");
        prg.setCancelable(false);
        rest=new RestClient(getApplicationContext());

        Bundle extras=getIntent().getExtras();

        id_edit=extras.getString("id_edit");


        //Toast.makeText(getApplicationContext(),"id: "+id_edit,Toast.LENGTH_LONG).show();
        ubahprofile(id_edit);
    }

    public void editproses(View view){
        Toast.makeText(this, "ini id user "+id_edit, Toast.LENGTH_SHORT).show();
        StringRequest ubahprofile=new StringRequest(Request.Method.POST, RestClient.EDIT_PROFILE+id_edit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prg.show();


                try{
                    JSONObject obj=new JSONObject(response);
                    Boolean status=obj.getBoolean("status");

                    if(status){


                        String hasilreg=obj.getString("pesan");

                        Toast.makeText(getApplicationContext(),hasilreg,Toast.LENGTH_LONG).show();

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
                prg.hide();

                Intent i=new Intent(getApplicationContext(),AmbilProfile.class);
                i.putExtra("tokensent", id_edit);
                startActivity(i);
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
                params.put("first_name", firstedit.getText().toString());
                params.put("last_name", lastedit.getText().toString());
                params.put("username", usernameditvar.getText().toString());
                //params.put("status", statusedit.getText().toString());
                return params;
            }


        }; ubahprofile.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplicationContext()).add(ubahprofile);

    }




    private void ubahprofile(String idku){
        StringRequest ubahprofile=new StringRequest(Request.Method.GET, RestClient.SHOW_PROFILE+idku, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prg.show();


                try{
                    JSONObject obj=new JSONObject(response);
                    Boolean status=obj.getBoolean("status");


                    if(status==true){

                        JSONObject arrayku=obj.getJSONObject("pesan");
                        Log.e("TAMPIL EDIT",String.valueOf("arrayku"));



                        String usernameeditstr=arrayku.getString("username");
                       // String statused=obj1.getString("status");
                        String first_name=arrayku.getString("first_name");
                        //String email2=obj1.getString("email");
                        String last_name=arrayku.getString("last_name");


                        Log.e("ini data", first_name);


                      //  setContentView(R.layout.info_profil);
                        usernameditvar=(EditText)findViewById(R.id.usernameedit);
                        firstedit=(EditText)findViewById(R.id.firstnameedit);
                        lastedit=(EditText)findViewById(R.id.lastnameedit);
                        //emailedit=(EditText)findViewById(R.id.emaiedit);
                       // displayedit=(EditText)findViewById(R.id.displayedit);

                       // statusedit.setText(statused, TextView.BufferType.EDITABLE);
                        firstedit.setText(first_name, TextView.BufferType.EDITABLE);
                        lastedit.setText(last_name, TextView.BufferType.EDITABLE);
                        usernameditvar.setText(usernameeditstr, TextView.BufferType.EDITABLE);
                        //emailedit.setText(email2, TextView.BufferType.EDITABLE);
                        //displayedit.setText(displayed, TextView.BufferType.EDITABLE);




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
                prg.hide();


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
//
//            @Override
//            protected Map<String, String> getParams(){
//
//                Map<String, String> params=new HashMap<String, String>();
//                params.put("username",uname.getText().toString());
//                params.put("password",pwd.getText().toString());
//                return params;
//            }


        }; ubahprofile.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplicationContext()).add(ubahprofile);

    }



}
