package prak.com.sproject;

/**
 * Created by Yoza on 16/06/2016.
 */

public class BeanClassForDoing {

    private String juduldoing;

    public String getJuduldoing() {
        return juduldoing;
    }

    public void setJuduldoing(String juduldoing) {
        this.juduldoing = juduldoing;
    }

    @Override
    public String toString() {
        return "BeanClassForDoing{" +
                "judul='" + juduldoing + '\'' +
                '}';
    }
}
