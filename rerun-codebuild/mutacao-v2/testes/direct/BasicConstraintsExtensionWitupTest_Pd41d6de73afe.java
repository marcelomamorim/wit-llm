/*
 * @test
 * @summary Test sun.security.x509.BasicConstraintsExtension.get(String) method
 * @run main BasicConstraintsExtensionWitupTest_Pd41d6de73afe
 */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest_Pd41d6de73afe {
    public static void main(String[] args) throws Exception {
        testGetIsCa();
        testGetPathLen();
        testGetInvalidName();
    }

    private static void testGetIsCa() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 5);
        Object val = ext.get("is_ca");
        if (!(val instanceof Boolean)) {
            throw new RuntimeException("Expected Boolean for is_ca");
        }
        Boolean b = (Boolean) val;
        if (!b.booleanValue()) {
            throw new RuntimeException("Expected true for is_ca");
        }
    }

    private static void testGetPathLen() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, 10);
        Object val = ext.get("path_len");
        if (!(val instanceof Integer)) {
            throw new RuntimeException("Expected Integer for path_len");
        }
        Integer i = (Integer) val;
        if (i.intValue() != 10) {
            throw new RuntimeException("Expected 10 for path_len");
        }
    }

    private static void testGetInvalidName() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(false, 0);
            ext.get("invalid_name");
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
