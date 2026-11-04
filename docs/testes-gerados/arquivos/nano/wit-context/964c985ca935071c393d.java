/* @test    @summary Test for sun.security.x509.CRLNumberExtension.encode(OutputStream) method    @run main CRLNumberExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import sun.security.x509.CRLNumberExtension;

public class CRLNumberExtensionWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithNullExtensionValue();
        testEncodeWithLargeCrlNumber();
        testEncodeThrowsIOException();
    }

    private static void testEncodeWithNullExtensionValue() {
        try {
            BigInteger crlNum = BigInteger.valueOf(12345);
            CRLNumberExtension ext = new CRLNumberExtension(crlNum);
            // Force extensionValue to null to simulate uninitialized state
            ext.extensionValue = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result != null && result.length > 0) {
                System.out.println("testEncodeWithNullExtensionValue passed.");
            } else {
                System.err.println("testEncodeWithNullExtensionValue failed: empty output.");
            }
        } catch (Exception e) {
            System.err.println("testEncodeWithNullExtensionValue failed with exception: " + e);
        }
    }

    private static void testEncodeWithLargeCrlNumber() {
        try {
            BigInteger largeNumber = new BigInteger("123456789012345678901234567890");
            CRLNumberExtension ext = new CRLNumberExtension(largeNumber);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result != null && result.length > 0) {
                System.out.println("testEncodeWithLargeCrlNumber passed.");
            } else {
                System.err.println("testEncodeWithLargeCrlNumber failed: empty output.");
            }
        } catch (Exception e) {
            System.err.println("testEncodeWithLargeCrlNumber failed with exception: " + e);
        }
    }

    private static void testEncodeThrowsIOException() {
        try {
            BigInteger crlNum = BigInteger.valueOf(12345);
            CRLNumberExtension ext = new CRLNumberExtension(crlNum);
            // Create a custom OutputStream that throws IOException
            OutputStream faultyOut = new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    throw new IOException("Simulated IO failure");
                }
            };
            ext.encode(faultyOut);
            System.err.println("testEncodeThrowsIOException failed: exception not thrown.");
        } catch (IOException e) {
            if ("Simulated IO failure".equals(e.getMessage())) {
                System.out.println("testEncodeThrowsIOException passed.");
            } else {
                System.err.println("testEncodeThrowsIOException failed with unexpected exception: " + e);
            }
        } catch (Exception e) {
            System.err.println("testEncodeThrowsIOException failed with exception: " + e);
        }
    }
}
