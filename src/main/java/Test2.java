import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Random;

public class Test2 {

    private ArrayList<String> list = new ArrayList<String>();

    @Test
    public void test2() {
        System.out.println("Test 2");
        list.add("usertype:S");
        list.add("first:T");
        list.add("last:T");
        list.add("email:T");
        list.add("email_verify:T");
        list.add("cpso:T");
        list.add("specialty:S");
        list.add("birthdate:D");
        list.add("gender:S");
        list.add("language:S");
        list.add("prov:S");

        for (String ll : list) {
            String[] str = ll.split(":");
            String idElem = str[0];
            String inputType = str[1];
            saveResult(idElem);
        }
    }

    private void saveResult(String idElem) {
        int x = (new Random()).nextInt(2);
        System.out.println(x);

    }
}
