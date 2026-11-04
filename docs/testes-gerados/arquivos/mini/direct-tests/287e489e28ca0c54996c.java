/*
 * @test
 * @summary Test javax.net.ssl.SNIHostName.createSNIMatcher(String) method
 * @run main SNIHostNameWitupTest
 */

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIMatcher;

public class SNIHostNameWitupTest {
    public static void main(String[] args) {
        testCreateSNIMatcherWithValidRegex();
        testCreateSNIMatcherWithNullRegex();
    }

    private static void testCreateSNIMatcherWithValidRegex() {
        String regex = "^example\\.com$";
        SNIMatcher matcher = SNIHostName.createSNIMatcher(regex);
        if (matcher == null) {
            throw new RuntimeException("createSNIMatcher returned null for valid regex");
        }
    }

    private static void testCreateSNIMatcherWithNullRegex() {
        try {
            SNIHostName.createSNIMatcher(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("regular expression cannot be null")) {
                throw new RuntimeException("NullPointerException message unexpected: " + msg);
            }
        }
    }
}
