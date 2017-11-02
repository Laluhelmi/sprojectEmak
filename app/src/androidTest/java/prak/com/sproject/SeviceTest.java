package prak.com.sproject;

import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Yoza on 06/09/2016.
 */

public class SeviceTest extends AndroidTestCase {

    @Test
    public void testGetProfil() throws Exception{

        AmbilProfile objget=new AmbilProfile(getContext());

        objget.tampilIdku("3eXE5tba1NbX6ebUnKu11ODE2%2BGb1tLf");


    }

    @Test
    public void testGetProject() throws Exception{

        AmbilProfile objget=new AmbilProfile(getContext());

        objget.tampilIdku("3eXE5tba1NbX6ebUnKu11ODE2%2BGb1tLf");


    }
}
