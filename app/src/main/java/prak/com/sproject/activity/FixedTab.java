package prak.com.sproject.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.content.ContextCompat;
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

import prak.com.sproject.AmbilProfile;
import prak.com.sproject.BeanClassForDetail;
import prak.com.sproject.BeanClassForTodo;
import prak.com.sproject.DetailProjectAdapter;
import prak.com.sproject.DetailTodoAdapter;
import prak.com.sproject.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import prak.com.sproject.RestClient;
import prak.com.sproject.fragments.FourFragment;
import prak.com.sproject.fragments.OneFragment;
import prak.com.sproject.fragments.TwoFragment;
import prak.com.sproject.fragments.ThreeFragment;
import prak.com.sproject.fragments.FourFragment;

/**
 * Created by Yoza on 18/08/2016.
 */

public class FixedTab extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ProgressDialog prg;
    RestClient rest;
    String idproject,titleproject,usercht;

    String[] usernametim,jabatantim;
    //String[] judultodo,statustodo,estimasi;

    ArrayList<BeanClassForDetail> datadetail;
   // ArrayList<BeanClassForTodo> datatodo;

    DetailProjectAdapter detailadapter;
    //DetailTodoAdapter detailTodoAdapter;

    //ListView listtodo;

    TextView judul;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    MenuItem navho;
    Menu menu;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityfragment);

        prg=new ProgressDialog(this);
        prg.setMessage("Please Wait");
        prg.setCancelable(false);

        toolbar = (Toolbar) findViewById(R.id.toolbarfr);

        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            Drawable drawable = getResources().getDrawable(R.drawable.menu);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(newdrawable);
        }

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.navigation);

        View header=navigationView.getHeaderView(0);
        judul=(TextView)header.findViewById(R.id.namaku);

        rest=new RestClient(getApplicationContext());
        datadetail=new ArrayList<BeanClassForDetail>();

        Bundle extras=getIntent().getExtras();
        idproject=extras.getString("idprosent");
        titleproject=extras.getString("titlesent");
        usercht=extras.getString("iduser");
        Toast.makeText(this, "username"+usercht, Toast.LENGTH_SHORT).show();

        judul.setText("Project Name : "+titleproject);
        //titleproo.setText("Project Name : "+titleproject);

        menu=navigationView.getMenu();





        tampilanggota(idproject);





        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_daily,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);

                return true;

            case R.id.history_daily:
                //drawerLayout.openDrawer(Gravity.RIGHT);
                Intent i=new Intent(getApplicationContext(),HistoryDaily.class);

                i.putExtra("idprojecthisto",idproject);
                startActivity(i);

                return true;



        }
        return super.onOptionsItemSelected(item);
    }

    public String getMydata(){

        return idproject;
    }

    public String getUsercht(){
        return usercht;
    }


    public void tampilanggota(String idproject) {


        StringRequest anggota=new StringRequest(Request.Method.GET, "http://apiscrum.alfatech.id/index.php/app/show_tim_by_project/" + idproject + "?X-API-KEY=tifuad", new Response.Listener<String>() {
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


                        detailadapter = new DetailProjectAdapter(FixedTab.this, datadetail);
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
                String credentials = "PHASAR"+":"+"newstartupfromkampusuad";
                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                params.put("Authorization", auth);
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

    private void setupTabIcons() {

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorAccent));
        tabLayout.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("TODO");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.todo, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);


        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("DOING");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.doing, 0, 0);

        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("DONE");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.done, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("DAILY");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.chat, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "ONE");
        adapter.addFragment(new TwoFragment(), "TWO");
        adapter.addFragment(new ThreeFragment(), "THREE");
        adapter.addFragment(new FourFragment(), "FOUR");
        viewPager.setAdapter(adapter);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */





    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
