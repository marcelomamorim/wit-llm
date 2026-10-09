/* @test    @summary Test for SecurityManager.checkConnect(String, int, Object) with null host triggering NullPointerException    @run main SecurityManagerWitupTest_P3f7261358be0 */

import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest_P3f7261358be0 {

    public static void main(String[] args) {
        testCheckConnectNullHost();
    }

    public static void testCheckConnectNullHost() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkConnect(null, 80, null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception: " + t);
        }
    }
}
