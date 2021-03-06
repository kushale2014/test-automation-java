package tests;
import base.TestBase;
import org.testng.annotations.Test;
import pages.LoginPage;

public class TestLoginPage extends TestBase {

    private String url = "https://qatest-28flsd5.meshmd.com/SignIn?r=%2F";

    @Test
    public void test1() {
        super.start(url);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.verifyBtnSignin();
        loginPage.verifyAllLinks(url);

        super.finish();
    }

}
