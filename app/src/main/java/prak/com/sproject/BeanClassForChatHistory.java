package prak.com.sproject;

/**
 * Created by Yoza on 29/08/2016.
 */

public class BeanClassForChatHistory {


    private String tanggalchat;
    private String idprochat;

    public String getIdprochat() {
        return idprochat;
    }

    public void setIdprochat(String idprochat) {
        this.idprochat = idprochat;
    }

    public String getTanggalchat() {
        return tanggalchat;
    }

    public void setTanggalchat(String tanggalchat) {
        this.tanggalchat = tanggalchat;
    }

    @Override
    public String toString() {
        return "BeanClassForChatHistory{" +
                "tanggal='" + tanggalchat + '\'' +
                "idprojectchat'"+ idprochat+ '\'' +
                '}';
    }
}
