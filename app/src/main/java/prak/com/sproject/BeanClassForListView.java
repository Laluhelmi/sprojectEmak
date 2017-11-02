package prak.com.sproject;

/**
 * Created by admin on 3/22/2016.
 */
public class BeanClassForListView {
    private String idpro;
    private  String title;
    private String description;
    private String usernamechatbean;

//    public BeanClassForListView( String title, String description) {
//        this.title = title;
//        this.description = description;
//    }

    public String getUsernamechatbean(){
        return usernamechatbean;
    }

    public void setUsernamechatbean(String usernamechatbean){ this.usernamechatbean=usernamechatbean; }
    public String getIdpro() {
        return idpro;
    }

    public void setIdpro(String idpro) {
        this.idpro = idpro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BeanClassForListView{" +
                "id='"+idpro+'\''+
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
