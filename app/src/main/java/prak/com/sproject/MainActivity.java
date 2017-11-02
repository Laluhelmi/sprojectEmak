package prak.com.sproject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button login;
    //    TextView hasil,errormsg;
//    EditText uname,pwd;
    RestClient rest;
    ProgressDialog prg;
    String usern, passwo;
    String tokendapat;
    EditText email;
    EditText pass;


    TextView cont;
    // Integer id;
    // Button cekprof;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);


        db = new DBHelper(getApplicationContext());

        if (db.CheckIsDataAlreadyInDBorNot()) {

            String tok = String.valueOf(db.getaLL());
            this.tokendapat = tok;

            Intent i = new Intent(getApplicationContext(), AmbilProfile.class);
            i.putExtra("tokensent", tok);
            startActivity(i);


        }

        setContentView(R.layout.loginscrum);


        // getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

//        hasil=(TextView)findViewById(R.id.hasil);
//        errormsg=(TextView)findViewById(R.id.login_error);


        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        prg = new ProgressDialog(this);
        prg.setMessage("Tunggu ya.....");
        prg.setCancelable(false);
        rest = new RestClient(getApplicationContext());

        final TokenModel tokan = new TokenModel();


        login = (Button) findViewById(R.id.btnLogin);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("INI IN COOOOYYYY=", String.valueOf(RestClient.API_LOGIN));
                prg.show();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(login.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

                //  hasil.setText("");

                StringRequest cekprofile = new StringRequest(Request.Method.POST, RestClient.API_LOGIN, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        prg.hide();

                        try {
                            JSONObject obj = new JSONObject(response);
                            Boolean status = obj.getBoolean("status");

                            //  JSONObject pesan=obj.getJSONObject("pesan");
                            // String email=pesan.getString("email");
                            //String data=pesan.getString("data");
                            Log.e("INI status COOOOYYYY=", String.valueOf(status));
                            // Log.e("INI email COOOOYYYY=", email);

                            // Log.e("INI status COOOOYYYY=", stat);

                            if (status) {

                                JSONObject pesan = obj.getJSONObject("pesan");
                                String token = pesan.getString("token");
                                Log.e("Ini pesan enkrip", token);
                                tokan.pesantok = token;


                                if (Objects.equals(db.getToken(token), token)) {

                                    Toast.makeText(getApplicationContext(), "TOKEN UDAH ADA", Toast.LENGTH_LONG).show();
                                } else {
                                    db.addDataToken(tokan);
                                    Toast.makeText(getApplicationContext(), "Sukses log", Toast.LENGTH_LONG).show();


                                    String tokendb = String.valueOf(db.getToken(token));

                                    Intent i = new Intent(getApplicationContext(), AmbilProfile.class);

                                    i.putExtra("tokensent", tokendb);
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(), tokendb, Toast.LENGTH_LONG).show();
                                }


                            } else {
                                Log.e("INI status COOOOYYYY=", "kosssdong");
                                Toast.makeText(getApplicationContext(), "gagal log", Toast.LENGTH_LONG).show();
                            }


                            //  Log.e("", status);


//                                    usern=obj.getString("username");
//                                    passwo=obj.getString("password");
//                                int id=pesan.getInt("id");
//
//                                Log.e("INI IN COOOOYYYY=", String.valueOf(id));
//                                Intent i=new Intent(getApplicationContext(),AmbilProfile.class);
//
////                                i.putExtra("datauser", usern);
////                                i.putExtra("datapass", passwo);
//
//                                i.putExtra("id",id);
//
//                                startActivity(i);


                            // }


                            // JSONObject pesan=obj.getJSONObject("pesan");

                            //  String messag=pesan.getString("email");

                            //                     else if((status.equals("fail")) && (messag.equals(""))){
//                            else{
////                               String msg=obj.getString("pesan");
// Log.e("Ini messag id pesan",RestClient.API_LOGIN);
////                                Log.e("INI SALAH MESSAGE",msg);
//                                Toast.makeText(getApplicationContext(),"INI SALAAAAAH BROOOKU",Toast.LENGTH_LONG).show();
//                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        prg.hide();
                        NetworkResponse response = error.networkResponse;
                        if (response != null && response.data != null) {

                            rest.statusCode(response.statusCode, response.data);
                            // rest.message("salah user atau pass");

                            //  hasil.setText("gagal loh");
                            //Toast.makeText(getApplicationContext(),"isgi, ini sukses",Toast.LENGTH_LONG).show();

                        } else {


                            Toast.makeText(getApplicationContext(), "cek koneksi internet anda", Toast.LENGTH_LONG).show();

                        }
                    }
                }
                ) {
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
                        Map<String, String> params = new HashMap<>();
                        String credentials = "PHASAR" + ":" + "newstartupfromkampusuad";
                        String auth = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                        params.put("Authorization", auth);
                        params.put("X-API-KEY", RestClient.X_API_KEY);
                        return params;
                    }

                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email.getText().toString());
                        params.put("password", pass.getText().toString());
                        return params;
                    }


                };
                cekprofile.setRetryPolicy(new DefaultRetryPolicy(
                        3000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
                Volley.newRequestQueue(getApplicationContext()).add(cekprofile);


            }


        });
    }

    @Override
    public void onBackPressed() {
        backHandler();


    }

    public void backHandler() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                MainActivity.this
        );

        alertDialog.setTitle("Ingin Keluar Dari Aplikasi?");
        alertDialog.setMessage("Anda yakin ingin keluar?");
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }

                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();

    }


    public String gettoken() {

        return tokendapat;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
