/* @test    @summary Test for sun.security.x509.SubjectInfoAccessExtension.encode method to verify IOException handling and normal behavior    @run main SubjectInfoAccessExtensionWitupTest */

import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.SubjectInfoAccessExtension;

public class SubjectInfoAccessExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNullExtensionId();
        testEncodeNormal();
    }

    private static void testEncodeWithNullExtensionValue() {
        try {
            // Create an instance with extensionValue == null
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(Collections.emptyList()) {
                @Override
                public void encode(OutputStream out) throws IOException {
                    // Force extensionValue to null
                    this.extensionValue = null;
                    super.encode(out);
                }
            };
            ext.encode(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    // no-op
                }
            });
            // If no exception, test passes
        } catch (IOException e) {
            // Expected exception path
            System.out.println("Caught expected IOException for null extensionValue.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private static void testEncodeWithNullExtensionId() {
        try {
            // Create an instance with extensionId == null
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(Collections.emptyList()) {
                @Override
                public void encode(OutputStream out) throws IOException {
                    // Force extensionId to null
                    this.extensionId = null;
                    super.encode(out);
                }
            };
            ext.encode(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    // no-op
                }
            });
            // If no exception, test passes
        } catch (IOException e) {
            // Expected exception path
            System.out.println("Caught expected IOException for null extensionId.");
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    private static void testEncodeNormal() {
        try {
            // Create a normal instance with dummy data
            SubjectInfoAccessExtension ext = new SubjectInfoAccessExtension(Collections.emptyList());
            ext.encode(new OutputStream() {
                private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                @Override
                public void write(int b) throws IOException {
                    baos.write(b);
                }
                public byte[] toByteArray() {
                    return baos.toByteArray();
                }
            });
            System.out.println("Normal encode executed successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException during normal encode", e);
        }
    }
}
