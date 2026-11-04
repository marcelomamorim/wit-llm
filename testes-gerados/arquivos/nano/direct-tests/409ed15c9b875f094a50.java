/* @test    @summary Test for sun.security.x509.OIDName.subtreeDepth() method
 * @run main OIDNameWitupTest
 */

import sun.security.x509.OIDName;

public class OIDNameWitupTest {

    public static void main(String[] args) {
        testSubtreeDepthThrows();
    }

    public static void testSubtreeDepthThrows() {
        OIDName oidName = null;
        try {
            oidName = new OIDName("1.2.3.4");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create OIDName instance", e);
        }
        boolean exceptionThrown = false;
        try {
            oidName.subtreeDepth();
        } catch (UnsupportedOperationException e) {
            exceptionThrown = true;
            if (!"subtreeDepth() not supported for OIDName.".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        }
    }
}
