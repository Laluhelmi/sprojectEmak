package prak.com.sproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Reza on 4/15/2016.
 */
public class Register extends Activity {

    EditText emailreg,firstreg,lastreg,displayreg,statusreg,userreg,passreg;
    Button regis;
    RestClient rest;
    ProgressDialog prg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tambahdata);

        emailreg=(EditText)findViewById(R.id.emailreg);
        firstreg=(EditText)findViewById(R.id.firstreg);
        lastreg=(EditText)findViewById(R.id.lastreg);
        displayreg=(EditText)findViewById(R.id.displayreg);
        statusreg=(EditText)findViewById(R.id.statusreg);
        userreg=(EditText)findViewById(R.id.userreg);
        passreg=(EditText)findViewById(R.id.passreg);

        prg=new ProgressDialog(this);
        prg.setMessage("Tunggu ya.....");
        prg.setCancelable(false);
        rest=new RestClient(getApplicationContext());



    }

    public void reg(View view){

        StringRequest regist=new StringRequest(Request.Method.POST, RestClient.REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

             //   prg.hide();

                try{
                    JSONObject obj=new JSONObject(response);
                    Boolean error=obj.getBoolean("error");



                    String hasilreg=obj.getString("pesan");

                    Toast.makeText(getApplicationContext(),hasilreg,Toast.LENGTH_LONG).show();

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

            @Override
            protected Map<String, String> getParams(){

                Map<String, String> params=new HashMap<String, String>();
                params.put("email",emailreg.getText().toString());
                params.put("first_name",firstreg.getText().toString());
                params.put("last_name",lastreg.getText().toString());
                params.put("display_pic",displayreg.getText().toString());
                params.put("status",statusreg.getText().toString());
                params.put("username", userreg.getText().toString());
                params.put("password",passreg.getText().toString());

                return params;
           }


        }; regist.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplicationContext()).add(regist);


    }
}
