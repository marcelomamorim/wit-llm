/* @test    @summary Test for BasicConstraintsExtension.get(String) method handling known and unknown attribute names    @run main BasicConstraintsExtensionWitupTest_P6ab2aac59fa2 */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest_P6ab2aac59fa2 {

    public static void main(String[] args) throws Exception {
        testGetIsCa();
        testGetPathLen();
        testGetUnknownAttribute();
    }

    private static void testGetIsCa() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        Object result = ext.get("is_ca");
        if (!(result instanceof Boolean)) {
            throw new AssertionError("Expected Boolean for 'is_ca', got: " + result.getClass());
        }
        if (!((Boolean) result).booleanValue()) {
            throw new AssertionError("Expected true for 'is_ca'");
        }
    }

    private static void testGetPathLen() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 5);
        Object result = ext.get("path_len");
        if (!(result instanceof Integer)) {
            throw new AssertionError("Expected Integer for 'path_len', got: " + result.getClass());
        }
        if (((Integer) result).intValue() != 5) {
            throw new AssertionError("Expected 5 for 'path_len', got: " + result);
        }
    }

    private static void testGetUnknownAttribute() {
        BasicConstraintsExtension ext;
        try {
            ext = new BasicConstraintsExtension(true, 0);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create BasicConstraintsExtension", e);
        }
        try {
            ext.get("unknown_attribute");
            throw new AssertionError("Expected IOException for unknown attribute");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
