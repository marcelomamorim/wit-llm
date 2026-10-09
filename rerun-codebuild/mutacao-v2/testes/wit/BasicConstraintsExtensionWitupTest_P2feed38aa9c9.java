/*
 * @test
 * @summary Test sun.security.x509.BasicConstraintsExtension.set(String,Object) for valid and invalid inputs
 * @run main BasicConstraintsExtensionWitupTest_P2feed38aa9c9
 */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest_P2feed38aa9c9 {
    public static void main(String[] args) throws Exception {
        testSetValidIsCa();
        testSetValidPathLen();
        testSetInvalidName();
        testSetInvalidTypeForIsCa();
        testSetInvalidTypeForPathLen();
    }

    private static void testSetValidIsCa() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, 0);
        ext.set("is_ca", Boolean.TRUE);
        // No exception means pass
    }

    private static void testSetValidPathLen() throws IOException {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(false, 0);
        ext.set("path_len", Integer.valueOf(5));
        // No exception means pass
    }

    private static void testSetInvalidName() {
        BasicConstraintsExtension ext = null;
        try {
            ext = new BasicConstraintsExtension(false, 0);
            ext.set("invalid_name", Boolean.TRUE);
            throw new RuntimeException("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetInvalidTypeForIsCa() {
        BasicConstraintsExtension ext = null;
        try {
            ext = new BasicConstraintsExtension(false, 0);
            ext.set("is_ca", "not a Boolean");
            throw new RuntimeException("Expected IOException for invalid type for is_ca");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value should be of type Boolean")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    private static void testSetInvalidTypeForPathLen() {
        BasicConstraintsExtension ext = null;
        try {
            ext = new BasicConstraintsExtension(false, 0);
            ext.set("path_len", "not an Integer");
            throw new RuntimeException("Expected IOException for invalid type for path_len");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute value should be of type Integer")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
