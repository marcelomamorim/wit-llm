/*
 * @test
 * @summary Test for SecurityManager.checkLink(String)
 * @run main SecurityManagerWitupTest_Pbda13b1403e5
 */

import java.lang.SecurityManager;

public class SecurityManagerWitupTest_Pbda13b1403e5 {
    public static void main(String[] args) {
        testCheckLinkWithNull();
        testCheckLinkWithValidString();
    }

    static void testCheckLinkWithNull() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkLink(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    static void testCheckLinkWithValidString() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkLink("testLib");
            System.out.println("PASS: checkLink with valid string executed without exception");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
