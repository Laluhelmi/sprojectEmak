package prak.com.sproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Reza on 4/23/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "basisdata";
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_ID = "id";
    private static final String KEY_TOKRN = "name";
    private static final String KEY_CHAT_USER = "chat_user";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE if not exists Mytoken (_id integer auto_increment primary key,tokenku TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTableToken(SQLiteDatabase db) {

    }

    public long addDataToken(TokenModel tok) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues NilaiBaru = new ContentValues();
        NilaiBaru.put("tokenku", tok.pesantok);

        long insert = db.insert("Mytoken", null, NilaiBaru);
        Log.d("INI QUERY INSERT",String.valueOf(insert));
        return insert;
    }

//    public void editDataMahasiswa(SQLiteDatabase databasis, String nim, String nama){
//        ContentValues UbahNilai = new ContentValues();
//        UbahNilai.put("Nama", nama);
//        databasis.update("nama", UbahNilai, "nim"+nim, null);
//    }
//
//    public void deleteDataMahasiswa(SQLiteDatabase databasis, String pesan){
//        databasis.delete("Mytoken", "nim"+pesan, null);
//    }

    public void deleteAll()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("Delete from Mytoken");
        db.close();
    }
    public String cektoken(String pesan) {
        SQLiteDatabase db = this.getReadableDatabase();

        String cekQuery = "SELECT * FROM Mytoken WHERE tokenku =" + pesan;
        Log.d("Ini error cek queery", cekQuery);

        return cekQuery;

    }

    public boolean CheckIsDataAlreadyInDBorNot() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String Query = "Select * from Mytoken";
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public String getaLL() {
        SQLiteDatabase db = this.getReadableDatabase();

        String tokenvalue=null;
        String selectQuery = "SELECT * FROM Mytoken";
        Log.d("INI ERROR QUERY", selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToLast())
            tokenvalue=String.valueOf(c.getString(c.getColumnIndex("tokenku")));
        Log.e("INI ISI TOKEN DARI DB",""+tokenvalue);
        c.close();
        db.close();


        return tokenvalue;

    }
    public String getToken(String pesan) {
        SQLiteDatabase db = this.getReadableDatabase();

        String tokenvalue=null;
        String selectQuery = "SELECT * FROM Mytoken WHERE tokenku = ?";
        Log.d("INI ERROR QUERY", selectQuery);

        Cursor c = db.rawQuery(selectQuery, new String[]{pesan});

        if (c.moveToLast())
            tokenvalue=String.valueOf(c.getString(c.getColumnIndex("tokenku")));
        Log.e("INI ISI TOKEN DARI DB",""+tokenvalue);
        c.close();
        db.close();


        return tokenvalue;

    }






}
