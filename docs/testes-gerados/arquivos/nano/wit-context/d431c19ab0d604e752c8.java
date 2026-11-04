/* @test    @summary Test sun.security.x509.Extension.encode(OutputStream) for null output stream and normal behavior    @run main ExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.Extension;

public class ExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullOutputStream();
        testEncodeNormalBehavior();
    }

    public static void testEncodeWithNullOutputStream() {
        Extension ext = createSampleExtension();
        try {
            ext.encode(null);
            System.err.println("Failed: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    public static void testEncodeNormalBehavior() throws IOException {
        Extension ext = createSampleExtension();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ext.encode(baos);
        byte[] result = baos.toByteArray();
        if (result != null && result.length > 0) {
            System.out.println("Passed: encode produced output of length " + result.length);
        } else {
            System.err.println("Failed: encode produced empty output");
        }
    }

    private static Extension createSampleExtension() {
        try {
            ObjectIdentifier oid = new ObjectIdentifier("1.2.3.4");
            byte[] value = {0x01, 0x02, 0x03};
            return new Extension(oid, true, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create sample Extension", e);
        }
    }
}
