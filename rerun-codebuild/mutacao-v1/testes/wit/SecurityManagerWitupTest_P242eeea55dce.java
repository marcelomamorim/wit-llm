/* @test    @summary Test for SecurityManager.checkAccept(String, int) with null host to trigger NullPointerException    @run main SecurityManagerWitupTest_P242eeea55dce */

import java.lang.SecurityManager;

public class SecurityManagerWitupTest_P242eeea55dce {

    public static void main(String[] args) {
        SecurityManager sm = new SecurityManager();
        boolean exceptionThrown = false;
        try {
            sm.checkAccept(null, 80);
            throw new AssertionError("Expected NullPointerException was not thrown");
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("NullPointerException was not thrown as expected");
        }
    }
}
