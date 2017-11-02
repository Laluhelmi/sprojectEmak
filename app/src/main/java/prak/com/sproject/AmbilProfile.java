package prak.com.sproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Reza on 4/24/2016.
 */
public class AmbilProfile extends AppCompatActivity{
    ProgressDialog prg;
    RestClient rest;
    TextView userku,firstku,lastku,emailku,register,notif;
    int id;
    String tokenSent,myuser;
    private ImageView imageViewRound;

    ArrayList<BeanClassForListView> data;

    ArrayList<BeanClassForNotif> datanotif;

    DetailNotifAdapter notifAdapter;
    listViewAdapter listViewAdapter;

    ArrayAdapter<String> adapter;
    String[] listku,deskripsipro,listkunotif,deskripsinotif;
    ListView listViewpro,listviewnotif;
    Button editprofilbaru,logout;
    private Toolbar mtoolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView,navigationViewleft;
    DBHelper db;
    String appserveerurl="http://apiscrum.alfatech.id/index.php/app/recive_token";
    private Button addProject;
    private EditText input_name,type_project,input_or_type,working_hours,deskription;
    private Spinner positon,man_hours;
    private Button setdate,settime,create,close;

    public void definiton(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tampung);
        addProject = (Button)findViewById(R.id.addproject);
        db=new DBHelper(getApplicationContext());
        mtoolbar=(Toolbar)findViewById(R.id.toolbardash);
        setSupportActionBar(mtoolbar);

        if(getSupportActionBar()!=null) {
            Drawable drawable = getResources().getDrawable(R.drawable.menu);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        }

        /// /getSupportActionBar().setDisplayShowHomeEnabled(true);
        prg=new ProgressDialog(this);
        prg.setMessage("Please Wait");
        prg.setCancelable(false);

        listViewpro=(ListView)findViewById(R.id.listviewku);


        data=new ArrayList<BeanClassForListView>();
        datanotif=new ArrayList<BeanClassForNotif>();

        rest=new RestClient(getApplicationContext());
        editprofilbaru=(Button)findViewById(R.id.editku);
        logout=(Button)findViewById(R.id.logout);
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(AmbilProfile.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.form_input);

                List<String> positions = new ArrayList<>();
                positions.add("Scrum Master");
                positions.add("Produk Owner");
                positions.add("Deplover Team");
                List<String> manHourss = new ArrayList<String>();
                manHourss.add("20 man");
                manHourss.add("28 man");
                manHourss.add("36 man");

                input_name      = (EditText)findViewById(R.id.input);
                type_project    = (EditText)findViewById(R.id.input_type_project);
                input_or_type   = (EditText)findViewById(R.id.input_or_type);
                working_hours   = (EditText)findViewById(R.id.input_working_hours);
                deskription     = (EditText)findViewById(R.id.input_description);
                positon         = (Spinner)dialog.findViewById(R.id.spinner_position);
                man_hours       = (Spinner)dialog.findViewById(R.id.spiner_man_hours);
                setdate         = (Button)dialog.findViewById(R.id.set_date);
                settime         = (Button)dialog.findViewById(R.id.set_time);
                create          = (Button)dialog.findViewById(R.id.create);
                close           = (Button)dialog.findViewById(R.id.close);
                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter;
                dataAdapter = new ArrayAdapter<String>(AmbilProfile.this,
                R.layout.support_simple_spinner_dropdown_item,
                        positions);
                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // attaching data adapter to spinner
                positon.setAdapter(dataAdapter);

                dataAdapter = new ArrayAdapter<String>(AmbilProfile.this,
                        R.layout.support_simple_spinner_dropdown_item,
                        manHourss);
                man_hours.setAdapter(dataAdapter);

                final Calendar myCalendar = Calendar.getInstance();
                setdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                            }
                        };
                        new DatePickerDialog(AmbilProfile.this,onDateSetListener,
                                myCalendar
                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();;
                    }
                });
                settime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new TimePickerDialog(AmbilProfile.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {

                            }
                        },myCalendar.get(Calendar.HOUR),myCalendar.get(Calendar.MINUTE),true).show();
                    }
                });

                dialog.show();
            }
        });
        definiton();


        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout_home);
        navigationView=(NavigationView)findViewById(R.id.navigationhome);
        navigationViewleft=(NavigationView)findViewById(R.id.navigationhomeleft);

       // View header=navigationViewleft.getHeaderView(0);
        //notif=(TextView)header.findViewById(R.id.notifku);
        listviewnotif=(ListView)findViewById(R.id.listviewnotifku);

        //notif.setText("Ini diubah bro");





        Bundle extras=getIntent().getExtras();

        tokenSent=extras.getString("tokensent");

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.Fire_notif), Context.MODE_PRIVATE);
        final String token=sharedPreferences.getString(getString(R.string.Fire_token),"");

        Log.e("FIREBASEcom",token);



        editprofilbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit=new Intent(getApplicationContext(),Editprofil.class);

                edit.putExtra("id_edit",tokenSent);
                startActivity(edit);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteAll();

                Intent i=new Intent(getApplicationContext(),MainActivity.class);

                startActivity(i);

            }
        });




        Toast.makeText(this, "NI TOKEN FIREBASE "+token.toString(), Toast.LENGTH_SHORT).show();





        kirimtoken(tokenSent);
        tampilProject(tokenSent);
        tampilIdku(tokenSent);


        tampilnotif(tokenSent);

    }



    public String gettoken(){

        return tokenSent;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);

                return true;

            case R.id.action_notif:
                drawerLayout.openDrawer(Gravity.RIGHT);
                return true;

            case R.id.action_profil:

                drawerLayout.openDrawer(Gravity.LEFT);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        backHandler();


    }

    public void backHandler(){

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(
                AmbilProfile.this
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

    @Override
    protected void onResume() {
//
//        tampilProject(tokenSent);
//        tampilIdku(tokenSent);
        super.onResume();

    }

    private void kirimtoken(String tokenSent){
        final String tokensent=tokenSent;


        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.Fire_notif), Context.MODE_PRIVATE);
        final String token=sharedPreferences.getString(getString(R.string.Fire_token),"");

        Toast.makeText(this, "NI TOKEN FIREBASE"+token, Toast.LENGTH_SHORT).show();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, appserveerurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                String credentials = "PHASAR"+":"+"newstartupfromkampusuad";
                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                params.put("X-API-KEY","tifuad");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();
                params.put("fcm_token",token);
                params.put("token_user",tokensent);

                return params;

            }
        };
        MyFireNotif.getMyFire(AmbilProfile.this).addtoRequestque(stringRequest);





    }

    private void tampilIdku(String tokenSent) {

        Log.e("ini link ",String.valueOf(RestClient.SHOW_PROFILE)+tokenSent);
        StringRequest tampilkan=new StringRequest(Request.Method.GET, RestClient.SHOW_PROFILE+tokenSent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    Log.e("Ini mau ke method", "cob");


                try {
                    JSONObject objik = new JSONObject(response);
                    Boolean status = objik.getBoolean("status");



                    if (status) {

                        JSONObject pesan = objik.getJSONObject("pesan");

                        String usernameku = pesan.getString("username");
                        String firstnameku = pesan.getString("first_name");
                        String lastnameku = pesan.getString("last_name");
                        String emaildserverku = pesan.getString("email");
                        String regisku = pesan.getString("registered");


                        SharedPreferences sp =getSharedPreferences("edu.upi.yudiwbs.dataku",MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();

                        //tulis
                        ed.putString("nama", usernameku);

                        ed.commit();

                        //baca





                        userku = (TextView) findViewById(R.id.username);
                        firstku = (TextView) findViewById(R.id.first);
                       // lastku = (TextView) findViewById(R.id.last);
                        emailku = (TextView) findViewById(R.id.emailku);
                        //register = (TextView) findViewById(R.id.register);

                        prg.hide();
                        userku.setText(usernameku);
                        firstku.setText(firstnameku);
                        //lastku.setText(lastnameku);
                        emailku.setText(emaildserverku);
                        //register.setText(regisku);


                    } else {
                        String pesan = String.valueOf(objik.getString("pesan"));
                        Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_LONG).show();
                        Intent i=new Intent(getApplicationContext(),MainActivity.class);
                        Toast.makeText(getApplicationContext(), pesan, Toast.LENGTH_LONG).show();
                        startActivity(i);

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
                if (response!=null&&response.data!=null){

                    rest.statusCode(response.statusCode,response.data);

                }
                else {
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
                String credentials = "PHASAR"+":"+"newstartupfromkampusuad";
                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
                params.put("X-API-KEY",RestClient.X_API_KEY);
                return params;
            }

        }; tampilkan.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getApplicationContext()).add(tampilkan);


    }

    private void tampilProject(String tokenSent) {
        Log.e("ini link ",String.valueOf(RestClient.PROJECTLIST)+tokenSent);
        userku = (TextView) findViewById(R.id.username);
        StringRequest projectlist=new StringRequest(Request.Method.GET,RestClient.PROJECTLIST+tokenSent, new Response.Listener<String>() {
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

                            String idku=detailobj.getString("id_project");
                            String judulku = detailobj.getString("judul");
                            String deskripsi=detailobj.getString("deskripsi");

                            BeanClassForListView beanClass=new BeanClassForListView();
                            beanClass.setIdpro(idku);
                            beanClass.setTitle(judulku);
                            beanClass.setDescription(deskripsi);
                           // Toast.makeText(AmbilProfile.this, "Ini di ambilpro "+userku.getText(), Toast.LENGTH_SHORT).show();
                            beanClass.setUsernamechatbean(String.valueOf(AmbilProfile.this.userku.getText()));
                            data.add(beanClass);

                        }

                        prg.hide();

                        listViewAdapter=new listViewAdapter(AmbilProfile.this,data);

                        listViewpro.setAdapter(listViewAdapter);
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


        }; projectlist.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        Volley.newRequestQueue(getApplicationContext()).add(projectlist);


    }

    private void tampilnotif(String tokenSent) {
        Log.e("ini link ",String.valueOf(RestClient.SHOW_NOTIF)+tokenSent);
        StringRequest projectnotif=new StringRequest(Request.Method.GET,RestClient.SHOW_NOTIF+tokenSent, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ini judul ", "cobajudul");


                try {
                    JSONObject dashobj = new JSONObject(response);
                    Boolean status = dashobj.getBoolean("status");

                    if (status) {
                        JSONArray arrayproject = dashobj.getJSONArray("pesan");


                        Log.e("ini isi objek detailobj",String.valueOf(arrayproject));
                        listkunotif= new String[arrayproject.length()];
                        deskripsinotif=new String[arrayproject.length()];



                        for (int i = 0; i < arrayproject.length(); i++) {
                            JSONObject detailobj = arrayproject.getJSONObject(i);

;
                            String judulku = detailobj.getString("judul");
                            String deskripsi=detailobj.getString("status");


//                            listku[i] = judulku;
//                            deskripsipro[i]=deskripsi;

                            BeanClassForNotif beanClassnotif=new BeanClassForNotif();

                            beanClassnotif.setTitlenotif(judulku);
                            beanClassnotif.setDescriptionnotif(deskripsi);
                            datanotif.add(beanClassnotif);

                        }

                        notifAdapter=new DetailNotifAdapter(AmbilProfile.this,datanotif);

                        listviewnotif.setAdapter(notifAdapter);




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


        }; projectnotif.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        Volley.newRequestQueue(getApplicationContext()).add(projectnotif);
    }
}
