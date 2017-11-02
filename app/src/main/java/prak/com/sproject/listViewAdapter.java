package prak.com.sproject;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.graphics.PorterDuff;
        import android.graphics.PorterDuffXfermode;
        import android.graphics.Rect;
        import android.graphics.RectF;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;
        import prak.com.sproject.activity.FixedTab;
        import prak.com.sproject.fragments.OneFragment;

        import java.util.ArrayList;

/**
 * Created by admin on 3/22/2016.
 */
public class listViewAdapter extends BaseAdapter{
    private Context context;

    private ArrayList<BeanClassForListView> beanClassArrayList;




    public listViewAdapter(Context context,ArrayList<BeanClassForListView> beanClassArrayList) {



        this.context = context;

        this.beanClassArrayList = beanClassArrayList;


    }


    @Override
    public int getCount() {
        return beanClassArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanClassArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHoder viewHoder;
        if (convertView == null) {

            LayoutInflater layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview, parent, false);




            viewHoder = new ViewHoder();



            viewHoder.coba=(LinearLayout)convertView.findViewById(R.id.contact);
            viewHoder.titletxt = (TextView) convertView.findViewById(R.id.titleprro);
            viewHoder.descriptiontxt = (TextView) convertView.findViewById(R.id.descriptionku);

            convertView.setTag(viewHoder);

        } else {

            viewHoder = (ViewHoder) convertView.getTag();
        }

        //NavigationItems rowItem = (NavigationItems) getItem(position);

        BeanClassForListView beanClass = (BeanClassForListView) getItem(position);

        viewHoder.titletxt.setText(beanClass.getTitle());
        viewHoder.descriptiontxt.setText(beanClass.getDescription());
        viewHoder.coba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"posisi"+position+beanClassArrayList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                Intent i=new Intent(context.getApplicationContext(),FixedTab.class);
                i.putExtra("idprosent",beanClassArrayList.get(position).getIdpro());
                i.putExtra("titlesent",beanClassArrayList.get(position).getTitle());
                i.putExtra("iduser",beanClassArrayList.get(position).getUsernamechatbean());



                context.startActivity(i);
            }
        });



//       viewHoder.plus.setImageResource(beanClass.getImage());
//        viewHoder.min.setImageResource(beanClass.getImage());
        //viewHoder.no.setText(beanClass.getNo());


        return convertView;

    }


    private class ViewHoder{


        TextView titletxt;
        TextView descriptiontxt;
        LinearLayout coba;


    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }



}
