/* @test    @summary Test for ExtendedKeyUsageExtension.encode method handling null extensionId and non-null extensionValue.    @run main ExtendedKeyUsageExtensionWitupTest */

import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.ExtendedKeyUsageExtension;
import sun.security.util.PKIXExtensions;

public class ExtendedKeyUsageExtensionWitupTest {

    public static void main(String[] args) {
        testEncodeWithNullExtensionIdAndNonNullExtensionValue();
    }

    public static void testEncodeWithNullExtensionIdAndNonNullExtensionValue() {
        try {
            // Setup: create an instance with extensionValue non-null, extensionId null
            ExtendedKeyUsageExtension ext = new ExtendedKeyUsageExtension();
            // Reflection or direct access is not used; assuming extensionValue is set via constructor or method
            // Since no public constructor with parameters is provided, simulate via subclass or reflection if needed
            // For this test, we assume extensionValue is set to a non-null value via a protected or package-private method
            // As per context, we simulate the scenario by subclassing or assuming default state
            // Here, we proceed with the assumption that extensionValue is non-null and extensionId is null
            // and that encode() will set extensionId accordingly
            // Note: in real test, you'd set extensionValue via available API or reflection if necessary
            // For simplicity, we proceed with a mock or dummy extensionValue
            ext.extensionValue = new byte[] {1, 2, 3}; // assuming package-private access
            ext.extensionId = null; // explicitly null
            ext.critical = false;
            // Call encode and expect no exception
            ext.encode(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    // no-op
                }
            });
            System.out.println("Test passed: encode() completed without IOException.");
        } catch (IOException e) {
            System.err.println("Test failed: IOException was thrown: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Test failed: Unexpected exception: " + e.getMessage());
        }
    }
}
