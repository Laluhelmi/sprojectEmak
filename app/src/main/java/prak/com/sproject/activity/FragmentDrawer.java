package prak.com.sproject.activity;
        import android.app.ProgressDialog;
        import  android.content.Context;
        import android.os.Bundle;
        import android.support.design.widget.NavigationView;
        import android.support.v4.app.Fragment;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v4.widget.SwipeRefreshLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Base64;
        import android.util.Log;
        import android.view.GestureDetector;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.MotionEvent;
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
        import java.util.List;
        import java.util.Map;

        import prak.com.sproject.BeanClassForDetail;
        import prak.com.sproject.BeanClassForTodo;
        import prak.com.sproject.DBHelper;
        import prak.com.sproject.DetailTodoAdapter;
        import prak.com.sproject.RestClient;
        import prak.com.sproject.model.NavDrawerItem;
        import prak.com.sproject.R;
        import prak.com.sproject.adapter.NavigationDrawerAdapter;

/**
 * Created by Yoza on 24/08/2016.
 */

public class FragmentDrawer extends Fragment{


    ProgressDialog prg;
    RestClient rest;
    String idproject,titleproject,token;

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
    DBHelper db;





    private static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView recyclerView,recyclerViewRight;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;

    public FragmentDrawer() {

    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public List<NavDrawerItem> getData(String token) {
        final List<NavDrawerItem> data = new ArrayList<>();



        StringRequest todo=new StringRequest(Request.Method.GET, "http://apiscrum.alfatech.id/index.php/app/notif_add_to_project/"+token, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject detailobjtoo = new JSONObject(response);
                    Boolean status = detailobjtoo.getBoolean("status");

                    Log.e("INI MASUK TAMPL",String.valueOf(status));

                    if (status) {

                        datatodo.clear();
                        JSONArray detailtodo = detailobjtoo.getJSONArray("pesan");

                        Log.e("todo error", String.valueOf(detailtodo));
                        //judultodo = new String[detailtodo.length()];
                        //estimasi = new String[detailtodo.length()];
                        // statustodo = new String[detailtodo.length()];

                        for (int i = 0; i < detailtodo.length(); i++) {
                            JSONObject moredetailtodo = detailtodo.getJSONObject(i);

                            String judultimvar = moredetailtodo.getString("jabatan");
                            //String estimasitimvar = moredetailtodo.getString("estimasi");
                            // String statustodotimvar=moredetailtodo.getString("status");

                          //  BeanClassForTodo beandetailtodo = new BeanClassForTodo();
                            //beandetailtodo.setJudultodo(judultimvar);
                            //beandetail.setJabatantimku(jabatantimvar);
                            //menu.add(i+1+"."+beandetail.getUsernametimku()+"("+beandetail.getJabatantimku()+")");


                                NavDrawerItem navItem = new NavDrawerItem();
                                navItem.setTitle(judultimvar);


                            data.add(navItem);



                        }


//                        adapter = new NavigationDrawerAdapter(getActivity(), data);
//                        recyclerView.setAdapter(adapter);
//
//                        detailTodoAdapter = new DetailTodoAdapter(getActivity(), datatodo);
                        //listtim.setAdapter(detailadapter);


                    }
                    else if(status==false) {

                        listtodo.setVisibility(View.GONE);
                        error.setVisibility(View.VISIBLE);

                       // swipeempty.setRefreshing(false);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            //    swipeRefreshLayout.setRefreshing(false);
            }

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
                Map<String,String> header=new HashMap<>();
                String credentials = "PHASAR"+":"+"newstartupfromkampusuad";
                String auth = "Basic "+ Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                header.put("Authorization", auth);
                header.put("X-API-KEY",RestClient.X_API_KEY);
                return header;
            }


        }; todo.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        Volley.newRequestQueue(getActivity().getApplicationContext()).add(todo);







        // preparing navigation drawer items
//        for (int i = 0; i < titles.length; i++) {
//            NavDrawerItem navItem = new NavDrawerItem();
//            navItem.setTitle(titles[i]);
//            data.add(navItem);
//        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        prg=new ProgressDialog(getActivity());
        prg.setMessage("Please Wait");
        prg.setCancelable(false);
        listtodo=(ListView)layout.findViewById(R.id.listviewtodo);


        rest=new RestClient(getActivity().getApplicationContext());
        datatodo=new ArrayList<BeanClassForTodo>();
        error=(TextView)layout.findViewById(R.id.error);


        db = new DBHelper(getActivity().getApplicationContext());

        if (db.CheckIsDataAlreadyInDBorNot()) {

            this.token = String.valueOf(db.getaLL());

        }

        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new NavigationDrawerAdapter(getActivity(), getData(token));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }
}
