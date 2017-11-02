package prak.com.sproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yoza on 15/06/2016.
 */

public class DetailTodoAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<BeanClassForTodo> detailtodoarray;

    public DetailTodoAdapter(Context context, ArrayList<BeanClassForTodo> detailtodoarray) {
        this.context = context;
        this.detailtodoarray = detailtodoarray;
    }

    @Override
    public int getCount() {
        return detailtodoarray.size();
    }

    @Override
    public Object getItem(int position) {
        return detailtodoarray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

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

        BeanClassForTodo beanClass = (BeanClassForTodo) getItem(position);

        viewHoder.titletxt.setText(beanClass.getJudultodo());
       // viewHoder.descriptiontxt.setText(beanClass.getDescription());
//        viewHoder.coba.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(context,"posisi"+position+beanClassArrayList.get(position).getTitle(),Toast.LENGTH_SHORT).show();
//                Intent i=new Intent(context.getApplicationContext(),DetailProject.class);
//                i.putExtra("idprosent",beanClassArrayList.get(position).getIdpro());
//                i.putExtra("titlesent",beanClassArrayList.get(position).getTitle());
//
//
//
//                context.startActivity(i);
//            }
//        });



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
}
