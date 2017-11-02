package prak.com.sproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Yoza on 09/06/2016.
 */

public class DetailProjectAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<BeanClassForDetail> detailarraylist;


    public DetailProjectAdapter(Context context, ArrayList<BeanClassForDetail> detailarraylist) {
        this.context = context;
        this.detailarraylist = detailarraylist;
    }

    @Override
    public int getCount() {
        return detailarraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return detailarraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHoder viewhod;
        if(convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView=layoutInflater.inflate(R.layout.item,parent,false);
            viewhod=new ViewHoder();

            viewhod.usertxt=(TextView)convertView.findViewById(R.id.itam);
            convertView.setTag(viewhod);

        }
        else {
            viewhod=(ViewHoder)convertView.getTag();
        }

        BeanClassForDetail beandetil=(BeanClassForDetail) getItem(position);
        position=position+1;
        viewhod.usertxt.setText(position+"."+beandetil.getUsernametimku()+"("+beandetil.getJabatantimku()+")");


        return convertView;
    }

    private class ViewHoder{
        TextView usertxt;


    }
}
