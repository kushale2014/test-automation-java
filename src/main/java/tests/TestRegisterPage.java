package tests;
import base.TestBase;
import org.testng.annotations.Test;
import pages.RegisterPage;

import java.io.IOException;

public class TestRegisterPage extends TestBase {

    @Test
    public void test1() {
        String url = "https://qatest-28flsd5.meshmd.com/register";
        super.start(url);

        RegisterPage registerPage = new RegisterPage(driver);
        try {
            registerPage.inputDataFromExcel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.finish();
    }


}
