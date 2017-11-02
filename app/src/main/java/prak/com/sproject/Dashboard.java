package prak.com.sproject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by Reza on 4/14/2016.
 */
public class Dashboard extends Activity {
    Button cekprof;
        private Button addProject;
    TextView hasil,errormsg,name,first,last,email,regis;
    RestClient rest;
    ProgressDialog prg;
    String usname,passwor;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardscrum);
        addProject = (Button)findViewById(R.id.addproject);
        prg=new ProgressDialog(this);
        prg.setMessage("Tunggu ya.....");
        prg.setCancelable(false);
        rest=new RestClient(getApplicationContext());
        Bundle extras=getIntent().getExtras();
        id=extras.getString("tokensent");
        showprofile(id);

    }

    public void cekall(View view){
                prg.show();
              Intent i=new Intent(getApplicationContext(),ProfileInfo.class);
                startActivity(i);
    }

    public void fungsireg(View view){
        prg.show();

        Intent i=new Intent(getApplicationContext(),Register.class);

        startActivity(i);
    }

    public void editprofil(View view){
        Intent i=new Intent(getApplicationContext(),Editprofil.class);
        i.putExtra("id_edit",id);
        startActivity(i);
    }

    private void showprofile(String id){
        Log.e("INI ID DI RESPONSE",String.valueOf(id));
        StringRequest cekaja=new StringRequest(Request.Method.GET, RestClient.SHOW_PROFILE+"/"+id+"?X-API-KEY="+RestClient.X_API_KEY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                prg.hide();
                try{
                    JSONObject obj=new JSONObject(response);
                    Boolean status=obj.getBoolean("status");

                    Log.e("INI EROR DI TRY",String.valueOf(status));


                        String pesan=obj.getString("pesan");

                        Log.e("INI PESAN TAU NGGA?",pesan);
                        if(pesan=="trusted") {

                            JSONArray arrayku = obj.getJSONArray("data");

                            JSONObject obj1 = arrayku.getJSONObject(0);


                            String username = obj1.getString("username");
                            String first_name = obj1.getString("first_name");
                            String email2 = obj1.getString("email");
                            String last_name = obj1.getString("last_name");
                            String registered = obj1.getString("registered");

                            Log.e("ini USER OOOY=",username);
                            Log.e("ini password ooooiiii",first_name);

                            setContentView(R.layout.info_profil);
                            name = (TextView) findViewById(R.id.username);
                            first = (TextView) findViewById(R.id.first);
                         //   last = (TextView) findViewById(R.id.last);
                            email = (TextView) findViewById(R.id.emailku);
                          //  regis = (TextView) findViewById(R.id.register);

                            name.setText(username);
                            first.setText(first_name);
                            //last.setText(last_name);
                            email.setText(email2);
                            //regis.setText(registered);



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

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//                params.put("X-API-KEY",RestClient.X_API_KEY);
//                return params;
//            }
//
//            @Override
//            protected Map<String, String> getParams(){
//
//                Map<String, String> params=new HashMap<String, String>();
//                params.put("username",uname.getText().toString());
//                params.put("password",pwd.getText().toString());
//                return params;
//            }


        }; cekaja.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplicationContext()).add(cekaja);


    }

}
