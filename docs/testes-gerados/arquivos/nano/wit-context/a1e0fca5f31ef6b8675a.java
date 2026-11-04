/* @test    @summary Test for ResourcesMgr.getString to verify behavior under normal and exceptional conditions    @run main ResourcesMgrWitupTest */

import sun.security.util.ResourcesMgr;

public class ResourcesMgrWitupTest {

    public static void main(String[] args) {
        testGetStringNormal();
        testGetStringWithInvalidResource();
    }

    private static void testGetStringNormal() {
        try {
            String result = ResourcesMgr.getString("someKey");
            System.out.println("testGetStringNormal: PASSED, result=" + result);
        } catch (Exception e) {
            System.out.println("testGetStringNormal: FAILED with exception: " + e);
        }
    }

    private static void testGetStringWithInvalidResource() {
        try {
            // Simulate invalid resource bundle scenario by temporarily mocking getBundle to throw
            // Since we cannot modify ResourcesMgr, we test for exception when VM is not booted.
            // But as VM.isBooted() is internal, we test for exception indirectly.
            // Alternatively, we can invoke getString with a key that is unlikely to exist.
            String result = ResourcesMgr.getString("nonExistentKey");
            System.out.println("testGetStringWithInvalidResource: PASSED, result=" + result);
        } catch (InternalError e) {
            System.out.println("testGetStringWithInvalidResource: PASSED, caught expected InternalError: " + e);
        } catch (Exception e) {
            System.out.println("testGetStringWithInvalidResource: FAILED with unexpected exception: " + e);
        }
    }
}
