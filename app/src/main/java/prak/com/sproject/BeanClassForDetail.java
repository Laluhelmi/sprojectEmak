package prak.com.sproject;

/**
 * Created by Yoza on 09/06/2016.
 */

public class BeanClassForDetail {

    private String usernametimku;
    private String jabatantimku;

    public String getUsernametimku() {
        return usernametimku;
    }

    public void setUsernametimku(String usernametimku) {
        this.usernametimku = usernametimku;
    }

    public String getJabatantimku() {
        return jabatantimku;
    }

    public void setJabatantimku(String jabatantimku) {
        this.jabatantimku = jabatantimku;
    }

    @Override
    public String toString() {
        return "BeanClassForDetail{" +
                "title='" + usernametimku + '\'' +
                ", description='" + jabatantimku + '\'' +
                '}';
    }
}
