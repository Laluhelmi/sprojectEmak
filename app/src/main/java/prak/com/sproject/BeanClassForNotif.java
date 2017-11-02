package prak.com.sproject;

/**
 * Created by Yoza on 24/08/2016.
 */

public class BeanClassForNotif {

    private  String judulnoti;
    private String descriptionnotif;

//    public BeanClassForListView( String title, String description) {
//        this.title = title;
//        this.description = description;
//    }


    public String getTitle() {
        return judulnoti;
    }

    public void setTitlenotif(String title) {
        this.judulnoti = title;
    }

    public String getDescription() {
        return descriptionnotif;
    }

    public void setDescriptionnotif(String description) {
        this.descriptionnotif = description;
    }

    @Override
    public String toString() {
        return "BeanClassForNotif{" +
                ", title='" + judulnoti + '\'' +
                ", description='" + descriptionnotif + '\'' +
                '}';
    }
}
