package prak.com.sproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Yoza on 04/09/2016.
 */

public class FirebaseInstance extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        String recent_token= FirebaseInstanceId.getInstance().getToken();
        Log.d("SIAAAAAAAAAAL", "Refreshed token: " + recent_token);
        Toast.makeText(this, "Refreshed token"+recent_token, Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.Fire_notif), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(getString(R.string.Fire_token),recent_token);
        editor.commit();
    }
}
