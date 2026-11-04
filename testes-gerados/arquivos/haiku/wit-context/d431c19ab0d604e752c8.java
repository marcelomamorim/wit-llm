/*
 * @test
 * @summary Verify that Extension.encode(OutputStream) rejects a null stream with NullPointerException
 * @modules java.base/sun.security.x509
 * @run main ExtensionWitupTest
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import sun.security.x509.Extension;

public class ExtensionWitupTest {
    public static void main(String[] args) throws Exception {
        Extension ext = new Extension();
        boolean thrown = false;
        try {
            ext.encode(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected NullPointerException for null OutputStream");
        }
    }
}
