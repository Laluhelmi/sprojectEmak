package prak.com.sproject;

/**
 * Created by Yoza on 16/08/2016.
 */

public class BeanClassForDone {

    private String juduldone;

    public String getJuduldone() {
        return juduldone;
    }

    public void setJuduldone(String juduldone) {
        this.juduldone = juduldone;
    }

    @Override
    public String toString() {
        return "BeanClassForDone{" +
                "judul='" + juduldone + '\'' +
                '}';
    }
}
