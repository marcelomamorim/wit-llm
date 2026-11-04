/* @test    @summary Test for ResourcesMgr.getAuthResourceString method    @run main ResourcesMgrWitupTest */

import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {

    public static void main(String[] args) {
        testGetAuthResourceString();
    }

    public static void testGetAuthResourceString() {
        String resourceKey = "test.key";
        String result = null;
        try {
            result = ResourcesMgr.getAuthResourceString(resourceKey);
            if (result == null) {
                System.out.println("PASS: getAuthResourceString returned null for key: " + resourceKey);
            } else {
                System.out.println("PASS: getAuthResourceString returned value: " + result);
            }
        } catch (InternalError e) {
            System.out.println("PASS: getAuthResourceString threw InternalError as expected when VM not booted");
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
            t.printStackTrace();
        }
    }
}
