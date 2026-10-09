/* @test    @summary Test BasicConstraintsExtension.get() method for known attribute names    @run main BasicConstraintsExtensionWitupTest_P6ab2aac59fa2 */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest_P6ab2aac59fa2 {
    public static void main(String[] args) throws IOException {
        // Test case: ca = true, pathLen = 5
        BasicConstraintsExtension ext = new BasicConstraintsExtension(Boolean.TRUE, true, 5);
        Object resultCa = ext.get("is_ca");
        Object resultPathLen = ext.get("path_len");
        if (!(resultCa instanceof Boolean) || ((Boolean) resultCa).booleanValue() != true) {
            throw new AssertionError("Expected is_ca to be true");
        }
        if (!(resultPathLen instanceof Integer) || ((Integer) resultPathLen).intValue() != 5) {
            throw new AssertionError("Expected path_len to be 5");
        }
        // Test case: ca = false, pathLen = -1 (not set)
        BasicConstraintsExtension ext2 = new BasicConstraintsExtension(Boolean.FALSE, false, -1);
        Object resultCa2 = ext2.get("is_ca");
        Object resultPathLen2 = ext2.get("path_len");
        if (!(resultCa2 instanceof Boolean) || ((Boolean) resultCa2).booleanValue() != false) {
            throw new AssertionError("Expected is_ca to be false");
        }
        if (!(resultPathLen2 instanceof Integer) || ((Integer) resultPathLen2).intValue() != -1) {
            throw new AssertionError("Expected path_len to be -1");
        }
        // Test case: invalid attribute name
        try {
            ext.get("unknown_attribute");
            throw new AssertionError("Expected IOException for unknown attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
