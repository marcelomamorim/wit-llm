/* @test    @summary Test for sun.security.x509.NetscapeCertTypeExtension.encode method    @run main NetscapeCertTypeExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.NetscapeCertTypeExtension;

public class NetscapeCertTypeExtensionWitupTest {

    public static void main(String[] args) {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue();
        testEncodeThrowsIOExceptionWhenExtensionIdIsNull();
    }

    private static void testEncodeWithNullExtensionValue() {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        ext.extensionValue = null;
        ext.extensionId = null;
        ext.critical = false;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("PASS: encode with null extensionValue produces output of length " + result.length);
            } else {
                System.out.println("FAIL: encode with null extensionValue produced empty output");
            }
        } catch (IOException e) {
            System.out.println("FAIL: encode with null extensionValue threw IOException: " + e);
        }
    }

    private static void testEncodeWithNonNullExtensionValue() {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        ext.extensionValue = new byte[] { 1, 2, 3 };
        ext.extensionId = null;
        ext.critical = true;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("PASS: encode with non-null extensionValue produces output of length " + result.length);
            } else {
                System.out.println("FAIL: encode with non-null extensionValue produced empty output");
            }
        } catch (IOException e) {
            System.out.println("FAIL: encode with non-null extensionValue threw IOException: " + e);
        }
    }

    private static void testEncodeThrowsIOExceptionWhenExtensionIdIsNull() {
        NetscapeCertTypeExtension ext = new NetscapeCertTypeExtension();
        ext.extensionValue = new byte[] { 1, 2, 3 };
        ext.extensionId = null; // force null extensionId to simulate error
        ext.critical = false;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            System.out.println("FAIL: encode did not throw IOException when extensionId is null");
        } catch (IOException e) {
            System.out.println("PASS: encode threw IOException as expected when extensionId is null: " + e.getMessage());
        }
    }
}
