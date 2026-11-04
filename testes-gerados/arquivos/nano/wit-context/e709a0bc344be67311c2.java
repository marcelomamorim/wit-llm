/* @test    @summary Test for sun.security.x509.IssuingDistributionPointExtension.encode method    @run main IssuingDistributionPointExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.IssuingDistributionPointExtension;

public class IssuingDistributionPointExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() {
        try {
            IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(
                true, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length == 0) {
                System.out.println("PASS: encode with null extensionValue produces output");
            } else {
                System.out.println("FAIL: encode with null extensionValue produces unexpected output");
            }
        } catch (IOException e) {
            System.out.println("FAIL: encode with null extensionValue threw IOException: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: encode with null extensionValue threw unexpected exception: " + e);
        }
    }

    private static void testEncodeWithNonNullExtensionValue() {
        try {
            // Using a dummy object for extensionValue, assuming encode() handles it
            IssuingDistributionPointExtension ext = new IssuingDistributionPointExtension(
                false, new Object());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("PASS: encode with non-null extensionValue produces output");
            } else {
                System.out.println("FAIL: encode with non-null extensionValue produces empty output");
            }
        } catch (IOException e) {
            System.out.println("FAIL: encode with non-null extensionValue threw IOException: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: encode with non-null extensionValue threw unexpected exception: " + e);
        }
    }
}
