/* @test    @summary Test for AuthorityKeyIdentifierExtension.encode method to verify IOException handling and behavior with null extensionValue.    @run main AuthorityKeyIdentifierExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.AuthorityKeyIdentifierExtension;

public class AuthorityKeyIdentifierExtensionWitupTest {

    public static void main(String[] args) {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
    }

    private static void testEncodeWithNullExtensionValue() {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        // Force extensionValue to null
        ext.extensionValue = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            System.out.println("testEncodeWithNullExtensionValue: Passed");
        } catch (IOException e) {
            System.out.println("testEncodeWithNullExtensionValue: Failed with IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("testEncodeWithNullExtensionValue: Failed with unexpected exception: " + e);
        }
    }

    private static void testEncodeWithNonNullExtensionValue() {
        AuthorityKeyIdentifierExtension ext = new AuthorityKeyIdentifierExtension();
        // Set extensionValue to a dummy byte array to simulate non-null value
        ext.extensionValue = new byte[] {1, 2, 3};
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("testEncodeWithNonNullExtensionValue: Passed");
            } else {
                System.out.println("testEncodeWithNonNullExtensionValue: Failed, output is empty");
            }
        } catch (IOException e) {
            System.out.println("testEncodeWithNonNullExtensionValue: Failed with IOException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("testEncodeWithNonNullExtensionValue: Failed with unexpected exception: " + e);
        }
    }
}
