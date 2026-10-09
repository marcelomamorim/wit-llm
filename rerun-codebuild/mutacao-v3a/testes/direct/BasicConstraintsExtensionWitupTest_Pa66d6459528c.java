/* @test    @summary Test the get method of BasicConstraintsExtension for recognized attribute names    @run main BasicConstraintsExtensionWitupTest_Pa66d6459528c */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest_Pa66d6459528c {

    public static void main(String[] args) throws IOException {
        testGetIsCa();
        testGetPathLen();
        testGetInvalidName();
    }

    private static void testGetIsCa() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 3);
        Object result = ext.get("is_ca");
        if (!(result instanceof Boolean)) {
            throw new AssertionError("Expected Boolean for is_ca, got: " + result.getClass());
        }
        if (!result.equals(Boolean.TRUE)) {
            throw new AssertionError("Expected true for is_ca, got: " + result);
        }
    }

    private static void testGetPathLen() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, 0);
        Object result = ext.get("path_len");
        if (!(result instanceof Integer)) {
            throw new AssertionError("Expected Integer for path_len, got: " + result.getClass());
        }
        if (!result.equals(Integer.valueOf(0))) {
            throw new AssertionError("Expected 0 for path_len, got: " + result);
        }
    }

    private static void testGetInvalidName() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 1);
            ext.get("unknown_attribute");
            throw new AssertionError("Expected IOException for unrecognized attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + msg, e);
            }
        }
    }
}
