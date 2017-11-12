package prak.com.sproject;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class Bantuan extends AppCompatActivity {
    private ListView listView;
    private List<String>judul;
    private List<String>deskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Bantuan");
        listView = (ListView)findViewById(R.id.faq);
        judul    = new ArrayList<>(); deskripsi = new ArrayList<>();
        tambahJudul();
        tambahDeskripsi();
        Adapter adapter = new Adapter(judul,deskripsi,this);
        listView.setAdapter(adapter);

    }

    public void tambahDeskripsi(){
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");
        deskripsi.add("SCRUM adalah salah satu metode rekayasa perangkat lunak dengan menggunakan prinsip-prinsip pendekatan AGILE, yang bertumpu pada kekuatan kolaborasi tim, incremental product dan proses iterasi untuk mewujudkan hasil akhir. ...");

    }

    public void tambahJudul(){
        judul.add("Bagaimana alur kerja scrum ?");
        judul.add("Apa itu dengan Scrum Project Manajement?");
        judul.add("Bagaimana cara menentukan tim dalam scrum?");
        judul.add("Bagaimana cara membuat project baru pada aplikasi Scrum Project Management?");
        judul.add("Apa itu Scrum Master ?");
        judul.add("Apa itu Product Owner ?");
        judul.add("Apa itu Developer team pada scrum ?");
        judul.add("Bagaimana cara melakukan pembagian task yang harus dikerjakan ?");
        judul.add("Bagaimana alur pengerjaan task menggunakan scrum?");

    }

    class Adapter extends BaseAdapter{
        private List<String>judul,deskripsi;
        LayoutInflater inflater;
        Adapter(List<String> judul, List<String> des, Context context){
            this.deskripsi = des;
            this.judul     = judul;
            inflater       = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return judul.size();
        }

        @Override
        public Object getItem(int i) {
            return judul.get(i);
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view            = inflater.inflate(R.layout.faq_item,viewGroup,false);
            TextView judult = (TextView)view.findViewById(R.id.judul);
            final TextView desk_t = (TextView)view.findViewById(R.id.deskripsi);

            judult.setText(judul.get(i));
            judult.setTypeface(Typeface.DEFAULT_BOLD);

            desk_t.setText(deskripsi.get(i));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(desk_t.getVisibility() == View.GONE){
                        desk_t.setVisibility(View.VISIBLE);
                    }else      desk_t.setVisibility(View.GONE);

                }
            });
            desk_t.setVisibility(View.GONE);
            return view;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
    }
}
