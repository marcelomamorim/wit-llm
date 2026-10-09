/* @test    @summary Test to verify behavior of SecurityManager.checkAccept() for null host input.
 * @run main SecurityManagerWitupTest_P8a6554d2dcf0
 */

import java.lang.SecurityManager;
import java.security.Permission;

public class SecurityManagerWitupTest_P8a6554d2dcf0 {

    public static void main(String[] args) {
        testCheckAcceptWithNullHost();
    }

    public static void testCheckAcceptWithNullHost() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkAccept(null, 80);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
