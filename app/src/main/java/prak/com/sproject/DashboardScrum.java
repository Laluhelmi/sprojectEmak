package prak.com.sproject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Yoza on 27/05/2016.
 */
public class DashboardScrum extends Activity {
    private ImageView imageViewRound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboardscrum);



       // imageViewRound=(ImageView)findViewById(R.id.banar1);

        Bitmap icon= BitmapFactory.decodeResource(getResources(), R.drawable.photo);
        imageViewRound.setImageBitmap(icon);
    }
}
