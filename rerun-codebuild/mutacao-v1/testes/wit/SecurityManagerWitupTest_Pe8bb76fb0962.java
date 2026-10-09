/* @test    @summary Test for SecurityManager.checkLink(String) handling null argument    @run main SecurityManagerWitupTest_Pe8bb76fb0962 */

import java.lang.SecurityManager;

public class SecurityManagerWitupTest_Pe8bb76fb0962 {

    public static void main(String[] args) {
        testCheckLinkNull();
        testCheckLinkValid();
    }

    private static void testCheckLinkNull() {
        SecurityManager sm = new SecurityManager();
        boolean threwNullPointer = false;
        try {
            sm.checkLink(null);
        } catch (NullPointerException e) {
            threwNullPointer = true;
        }
        if (!threwNullPointer) {
            throw new AssertionError("Expected NullPointerException when calling checkLink with null");
        }
    }

    private static void testCheckLinkValid() {
        SecurityManager sm = new SecurityManager();
        try {
            sm.checkLink("testLib");
        } catch (SecurityException e) {
            throw new AssertionError("Unexpected SecurityException for valid lib");
        }
    }
}
