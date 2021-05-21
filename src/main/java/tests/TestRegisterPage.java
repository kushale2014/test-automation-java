package tests;
import base.TestBase;
import org.testng.annotations.Test;
import pages.RegisterPage;

import java.io.IOException;

public class TestRegisterPage extends TestBase {

    private String url = "https://qatest-28flsd5.meshmd.com/register";

    @Test
    public void test1() {
        super.start(url);

        RegisterPage registerPage = new RegisterPage(driver);
        try {
            registerPage.inputDataFromExcel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.finish();
    }

    @Test
    public void test2() {
        super.start(url);

        RegisterPage registerPage = new RegisterPage(driver);
        try {
            registerPage.validateFields();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.finish();
    }


}
