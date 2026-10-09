/*
 * @test
 * @summary Test for SecurityManager.checkLink(String lib)
 * @run main SecurityManagerWitupTest_P8b08778445bf
 */

import java.lang.SecurityManager;

public class SecurityManagerWitupTest_P8b08778445bf {

    public static void main(String[] args) {
        testCheckLinkWithNull();
        testCheckLinkWithValidString();
    }

    private static void testCheckLinkWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkLink(null);
            System.out.println("testCheckLinkWithNull: FAILED (expected NullPointerException)");
        } catch (NullPointerException e) {
            System.out.println("testCheckLinkWithNull: PASSED");
        } catch (Throwable t) {
            System.out.println("testCheckLinkWithNull: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testCheckLinkWithValidString() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkLink("testLib");
            System.out.println("testCheckLinkWithValidString: PASSED");
        } catch (Throwable t) {
            System.out.println("testCheckLinkWithValidString: FAILED (unexpected exception: " + t + ")");
        }
    }
}
