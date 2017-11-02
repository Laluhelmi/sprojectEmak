package prak.com.sproject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Yoza on 04/09/2016.
 */

public class MyFireNotif {
    private static Context mcontext;
    private static MyFireNotif myFire;
    private RequestQueue requestQueue;

    private MyFireNotif(Context context) {
        mcontext = context;
        requestQueue=getRequestQueue();

    }

    private RequestQueue getRequestQueue(){

        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(mcontext.getApplicationContext());
        }

        return requestQueue;
    }

    public static synchronized MyFireNotif getMyFire(Context context)
    {
        if (myFire==null)
        {
            myFire=new MyFireNotif(context);
        }
        return myFire;
    }

    public<T> void addtoRequestque(Request<T> request)
    {

        getRequestQueue().add(request);
    }
}
