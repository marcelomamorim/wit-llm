/*
 * @test
 * @summary Test sun.security.x509.BasicConstraintsExtension.delete(String) method
 * @run main BasicConstraintsExtensionWitupTest
 */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteValidAttributes();
        testDeleteInvalidAttributeThrowsIOException();
    }

    private static void testDeleteValidAttributes() throws IOException {
        // Test deleting IS_CA attribute
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 5);
        ext.delete(BasicConstraintsExtension.IS_CA);
        // After deleting IS_CA, ca should be false, so deleting again should not throw
        ext.delete(BasicConstraintsExtension.IS_CA);

        // Test deleting PATH_LEN attribute
        ext = new BasicConstraintsExtension(true, 5);
        ext.delete(BasicConstraintsExtension.PATH_LEN);
        // After deleting PATH_LEN, pathLen should be -1, so deleting again should not throw
        ext.delete(BasicConstraintsExtension.PATH_LEN);
    }

    private static void testDeleteInvalidAttributeThrowsIOException() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 5);
            ext.delete("invalid_attribute_name");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            // Expected exception
            String msg = e.getMessage();
            if (msg == null || !msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("IOException message unexpected: " + msg);
            }
        }
    }
}
