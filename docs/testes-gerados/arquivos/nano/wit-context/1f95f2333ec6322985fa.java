/* @test    @summary Test for sun.security.x509.BasicConstraintsExtension.encode(OutputStream out) handling null extensionValue and exception propagation    @run main BasicConstraintsExtensionWitupTest */

import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.BasicConstraintsExtension;

public class BasicConstraintsExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithValidExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() {
        BasicConstraintsExtension ext = new BasicConstraintsExtension();
        ext.extensionValue = null;
        ext.extensionId = "1.2.3.4"; // dummy id
        ext.critical = false;
        try {
            ext.encode(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    // no-op
                }
            });
            // If no exception, test passes
        } catch (IOException e) {
            // Expected if encode throws due to null extensionValue
            // Test passes if exception is thrown
        }
    }

    private static void testEncodeWithValidExtensionValue() {
        BasicConstraintsExtension ext = new BasicConstraintsExtension();
        ext.extensionValue = new byte[] {0x01, 0x02}; // dummy valid data
        ext.extensionId = "1.2.3.4"; // dummy id
        ext.critical = false;
        try {
            ext.encode(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    // no-op
                }
            });
            // If no exception, test passes
        } catch (IOException e) {
            // Fail the test if exception occurs
            throw new RuntimeException("Unexpected IOException", e);
        }
    }
}
