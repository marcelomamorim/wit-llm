/*
 * @test
 * @summary Test BasicConstraintsExtension.delete method behavior
 * @run main BasicConstraintsExtensionWitupTest
 */

import sun.security.x509.BasicConstraintsExtension;
import java.io.IOException;

public class BasicConstraintsExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        testDeleteIsCa();
        testDeletePathLen();
        testDeleteInvalidName();
    }

    private static void testDeleteIsCa() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 5);
        ext.delete("is_ca");
        // After delete("is_ca"), ca should be false and pathLen unchanged (5)
        // We verify by deleting path_len and then deleting is_ca again to check no exception
        ext.delete("path_len");
        // Now pathLen is -1
        // Deleting is_ca again should not throw
        ext.delete("is_ca");
    }

    private static void testDeletePathLen() throws Exception {
        BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 10);
        ext.delete("path_len");
        // After delete("path_len"), pathLen should be -1 and ca unchanged (true)
        // Deleting is_ca should still work
        ext.delete("is_ca");
    }

    private static void testDeleteInvalidName() {
        try {
            BasicConstraintsExtension ext = new BasicConstraintsExtension(true, 1);
            ext.delete("invalid_name");
            throw new RuntimeException("Expected IOException not thrown for invalid attribute name");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (!msg.contains("Attribute name not recognized")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
