package prak.com.sproject;

/**
 * Created by Yoza on 11/06/2016.
 */

public class BeanClassForTodo {

    private String judultodo;

    public String getJudultodo() {
        return judultodo;
    }

    public void setJudultodo(String judultodo) {
        this.judultodo = judultodo;
    }

    @Override
    public String toString() {
        return  "BeanClassForToDo{" +
                "judul='" + judultodo + '\'' +
                '}';
    }
}
