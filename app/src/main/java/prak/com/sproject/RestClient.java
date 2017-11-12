package prak.com.sproject;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Reza on 4/4/2016.
 */
public class RestClient {

   // SessionManager sessionManager;
    public Context context;
    HashMap<String, String> user;
    ///x-auth
//    public static final String API = "http://posmikro.com/register.php";
//    public static final String API = "http://192.168.43.105/posmikro/";
//    public static final String API = "http://scrumproject.16mb.com/";
    public static final String API = "http://192.168.43.219/sproject/scrumapi/index.php/";
    public static final String X_AUTH = "ad8ab9dc1a769dfad03f5a98d7f8b9349f75cca2";
    public static final String KEY_X_AUTH = "x-auth";
    public static final String X_API_KEY="tifuad";
    public static final String KEY="123456789";
    public static final String SHOW_PROFILE=API+"app/profile/";
    public static final String SHOW_NOTIF=API+"app/notif_add_to_project/";
    public static final String PROJECTLIST=API+"app/dashboard_scrum/";
    public static final String CHATLIST=API+"app/daily_chat/";
    public static final String REGIST=API+"users/register";
    public static final String EDIT_PROFILE=API+"app/edit_profile/";
    public static final String API_PROFILE_CEK=API+"profile/";
    public static final String TAMBAH_PROJECT = API+"app/tambahprojek";
    public static final String getProdukType = API+"app/getProjectType";
    public static final String KEY_X_AUTH_LOGIN = "x-auth-login";
    public static final String KEY_TOKEN = "token";

    public static final String KEY_ID = "id";
    public static final String KEY_NAMA_OUTLET = "namaoutlet";
    public static final String JSON_ARRAY = "outlet";
    ///register
    public static final String API_REGISTER = API + "api/auth/register";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    //login
    public static final String API_LOGIN = API + "app/login";
    //produk franchisor
    public static final String API_PRODUK_FRANCHISOR = API + "api/v1/franchisor/produk/";

    //produk kasir
    public static final String API_PRODUK_KASIR = API + "api/kasir/produk";
    //franchise
    public static final String API_FRANSCHISE = API + "api/franchisor/franchise";
    public static final String API_JENIS = API + "api/konfig/jenisusaha";
    public static final String KEY_NAMA_USAHA = "namausaha";
    public static final String KEY_TELEPON = "telepon";
    public static final String KEY_ALAMAT = "alamat";
    public static final String KEY_JENIS_USAHA = "jenis";
    public static final String KEY_LOGO = "logo";
    ///transaksi
    public static final String API_TRANSAKSI = API + "api/v1/kasir/transaksi";
    public static final String KEY_PRODUK_ID = "produk_id";
    public static final String KEY_KODE_TRANSAKSI = "kode_transaksi";
    public static final String KEY_TOT_ITEM = "tot_item";
    public static final String KEY_TOT_HARGA = "tot_harga";
    public static final String KEY_TGL_ORDER = "tgl_order";
    public static final String KEY_OUTLETID = "outlet_id";
    public static final String KEY_SYNC_AT = "synchronized_at";

    //produk
    public static final String API_GET_PRODUK = API + "api/v1/produk/";
    public static final String KEY_OUTLET = "outlet";

    //Buka Kasir
    public static final String API_BUKA_KASIR = API + "api/kasir/buka";

    ///produk
    public static final String API_PRODUK = API + "api/v1/kasir/produk";

    //pelanggan
    public static final String API_PELANGGAN = API + "api/v1/pelanggan";

    //bonus
    public static final String API_BONUS = API + "api/v1/bonus";
    public static final String API_ADD_BONUS = API + "api/v1/addbonus";

    public static String email, token, namaperusahaan, alamat, namaoutlet, idoutlet;
    public int  idoutletd;

    public RestClient(Context con){
        this.context = con.getApplicationContext();
    }
//    public String dataEmail(){
//        sessionManager = new SessionManager(context);
//        user = sessionManager.getUser();
//        email = user.get(SessionManager.EMAIL);
//        return email;
//    }
//    public String X_AUTH_LOGIN(){
//        sessionManager = new SessionManager(context);
//        user = sessionManager.getUser();
//        token = user.get(SessionManager.TOKEN);
//        return token;
//    }
//    public String dataNamaperusahaan(){
//        sessionManager = new SessionManager(context);
//        user = sessionManager.getData();
//        namaperusahaan = user.get(SessionManager.NAMAPERUSAHAAN);
//        return namaperusahaan;
//    }
//    public String dataAlamat(){
//        sessionManager = new SessionManager(context);
//        user = sessionManager.getData();
//        alamat = user.get(SessionManager.ALAMAT);
//        return alamat;
//    }
//    public String idOutlet(){
//        sessionManager = new SessionManager(context);
//        user = sessionManager.getOutlet();
//        idoutlet = user.get(SessionManager.OUTLETID);
//        return idoutlet;
//    }
//    public String namaOutlet(){
//        sessionManager = new SessionManager(context);
//        user = sessionManager.getOutlet();
//        namaoutlet = user.get(SessionManager.NAMAOUTLET);
//        return namaoutlet;
//    }
//
//
//
//
    public final void getErrors(String json){
        try {
            JSONObject object = new JSONObject(json);
            Boolean status = object.getBoolean("status");
            if (!status){
                //String msg = object.getString("message");
                JSONObject error = object.getJSONObject("error");
                String msgerror = "";
                //register, login
                if (error.has("email")){
                    JSONArray erromessage = error.getJSONArray("email");
                    for(int i=0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i)+"\n";
                    }
                }

                if (error.has("password")) {
                    JSONArray erromessage = error.getJSONArray("password");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                //transaksi
                if (error.has("produk_id")) {
                    JSONArray erromessage = error.getJSONArray("produk_id");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                if (error.has("kode_transaksi")) {
                    JSONArray erromessage = error.getJSONArray("kode_transaksi");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                if (error.has("tot_item")) {
                    JSONArray erromessage = error.getJSONArray("tot_item");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                if (error.has("tot_harga")) {
                    JSONArray erromessage = error.getJSONArray("tot_harga");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                if (error.has("tgl_order")) {
                    JSONArray erromessage = error.getJSONArray("tgl_order");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }

                //franchise
                if (error.has("namausaha")) {
                    JSONArray erromessage = error.getJSONArray("namausaha");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                if (error.has("jenis")) {
                    JSONArray erromessage = error.getJSONArray("jenis");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                if (error.has("telepon")) {
                    JSONArray erromessage = error.getJSONArray("telepon");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                if (error.has("alamat")) {
                    JSONArray erromessage = error.getJSONArray("alamat");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                if (error.has("logo")) {
                    JSONArray erromessage = error.getJSONArray("logo");
                    for(int i = 0;i<erromessage.length();i++){
                        msgerror += erromessage.getString(i);
                    }
                }
                message(msgerror);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void statusCode(int res, byte[] responData){
        String json;
        switch (res) {
            case 401:
                json = new String(responData);
                getErrors(json);
                break;
            case 402:
                json = new String(responData);
                getErrors(json);
                break;
            case 422:
                json = new String(responData);
                getErrors(json);
                break;
//            case 404:
//                message("Salah uname atau password");
//                break;
            case 403:
                message("Access forbidden");
                break;
            case 500:
                message("Internal server error");
                break;

        }
    }

    public final void message(String msg){
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show();
    }

   /* public void makeRequest(final String username, final String password){
        progressDialog = new ProgressDialog(context.getApplicationContext());
//        progressDialog = ProgressDialog.show(context.getApplicationContext(), "", "Silahkan tunggu..", true);
//        progressDialog.setCancelable(false);
        StringRequest postRequest = new StringRequest(Request.Method.POST, API_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    Boolean status = object.getBoolean("status");
                    if (status){
                        JSONArray data = object.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                String json;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 401:
                            json = new String(response.data);
                            getErrors(json);
                            //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                            break;
                        case 402:
                            json = new String(response.data);
                            getErrors(json);
                            //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                            break;
                        case 422:
                            json = new String(response.data);
                            getErrors(json);
                            //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            message("Internal server error");
                            break;
                        case 404:
                            message("Data not found");
                            break;
                        case 403:
                            message("Access forbiden");
                            break;
                    }
                }
                else {
                    message("Cek your conection");
                }
                progressDialog.dismiss();
            }
        }){
            @Override
            protected void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }
            @Override
            protected void deliverResponse(String response) {
                super.deliverResponse(response);
                progressDialog.show();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<>();
                params.put("x-auth", X_AUTH);
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", username);
                params.put("password", password);
                params.put("x-auth", RestClient.X_AUTH);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        requestQueue.add(postRequest);
    }*/

//    public  Context getAndSetMyContext(Context context) {
//        this.context = context;
//        return this.context;
//    }
/*    public void getCase(int statusCode) {
        String json;
        switch(statusCode){
            case 401:
                json = new String(String.valueOf(statusCode));
                getErrors(json);
                //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                break;
            case 402:
                json = new String(String.valueOf(statusCode));
                getErrors(json);
                //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                break;
            case 422:
                json = new String(String.valueOf(statusCode));
                getErrors(json);
                //Toast.makeText(getApplicationContext(),json,Toast.LENGTH_LONG).show();
                break;
            case 500:
                Toast.makeText(this.context, "Internal server error", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    */
}
