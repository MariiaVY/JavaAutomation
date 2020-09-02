//Create test with a usage of DataProvider, use at least 3 sets of data
package io.ctdev.HomeWork2;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class DataProviderTest {

    private String email;
    private String password;

    @Factory(dataProvider = "dataProviderMethod")
    public DataProviderTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @DataProvider
    public static Object[][] dataProviderMethod() {
        return new Object[][]{{"ccccgmail.com", "password1"}, {"aaaa@gmail.com", "password2"}, {"bbbb@gmail.com", "password2"}};
    }

    @Test
    public void testFirstLogin() {
        System.out.println("The email is " + email + " and password is " + password);
    }

    @Test
    public void testSecondLogin() {
        System.out.println("The email is " + email + " and password is " + password);
    }

    @Test
    public void testThirdLogin() {
        System.out.println("The email is " + email + " and password is " + password);
    }

}

