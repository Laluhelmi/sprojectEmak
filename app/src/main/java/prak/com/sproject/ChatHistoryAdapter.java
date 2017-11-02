package prak.com.sproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Yoza on 30/08/2016.
 */

public class ChatHistoryAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<BeanClassForChatHistory> beanChatClassArrayList;




    public ChatHistoryAdapter(Context context, ArrayList<BeanClassForChatHistory> beanChatClassArrayList) {



        this.context = context;

        this.beanChatClassArrayList = beanChatClassArrayList;


    }


    @Override
    public int getCount() {
        return beanChatClassArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return beanChatClassArrayList.get(position);
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

        BeanClassForChatHistory beanClass = (BeanClassForChatHistory) getItem(position);

        viewHoder.titletxt.setText(beanClass.getTanggalchat());
     //   viewHoder.descriptiontxt.setText(beanClass.getDescription());
        viewHoder.coba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tgl=beanChatClassArrayList.get(position).getTanggalchat();

//                Date dateku=null;
//                SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//                try {
//                    dateku=sdf.parse(tgl);
//                   // sdf.applyPattern("dd MM yyyy");
//
//                } catch (ParseException e) {
//                    Logger.getLogger(ChatHistoryAdapter.class.getName()).log(Level.SEVERE, null, context);
//
//                }
//                sdf=new SimpleDateFormat("dd MM yyyy");
//                Toast.makeText(context,"posisi"+sdf.format(dateku),Toast.LENGTH_SHORT).show();
//
//
//                int bulan=dateku.getMonth();
//
//                Toast.makeText(context,"Bulan skrng"+bulan+1,Toast.LENGTH_SHORT).show();
//
//                String yahun="";
//                switch (yahun){
//
//                    case "07":
//                        String bulanku="juli";
//                        Toast.makeText(context,"bulan "+bulanku,Toast.LENGTH_SHORT).show();
//                        break;
//
//                }




                Intent i=new Intent(context.getApplicationContext(),DetailChatHistory.class);
                i.putExtra("idprosentchat",beanChatClassArrayList.get(position).getIdprochat());
                i.putExtra("tanggalsent",beanChatClassArrayList.get(position).getTanggalchat());
                //i.putExtra("iduser",beanChatClassArrayList.get(position).getUsernamechatbean());



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
