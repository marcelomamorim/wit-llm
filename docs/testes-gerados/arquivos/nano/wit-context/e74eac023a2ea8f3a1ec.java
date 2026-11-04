/* @test    @summary Test for sun.security.x509.CRLReasonCodeExtension.encode(OutputStream out)    @run main CRLReasonCodeExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.CRLReasonCodeExtension;

public class CRLReasonCodeExtensionWitupTest {

    public static void main(String[] args) {
        testEncodeWithNullExtensionValue();
        testEncodeWithNonNullExtensionValue() ;
        testEncodeThrowsIOExceptionWhenExtensionIdNull();
    }

    private static void testEncodeWithNullExtensionValue() {
        try {
            CRLReasonCodeExtension ext = new CRLReasonCodeExtension(1); // reason code 1
            ext.extensionValue = null; // forcibly set to null to test encodeThis()
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result != null && result.length > 0) {
                System.out.println("PASS: encode with null extensionValue produces output") ;
            } else {
                System.out.println("FAIL: encode with null extensionValue produces no output") ;
            }
        } catch (Exception e) {
            System.out.println("FAIL: encode with null extensionValue threw exception: " + e);
        }
    }

    private static void testEncodeWithNonNullExtensionValue() {
        try {
            CRLReasonCodeExtension ext = new CRLReasonCodeExtension(2); // reason code 2
            // forcibly set extensionValue to a dummy byte array
            ext.extensionValue = new byte[] {0x01, 0x02, 0x03};
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result != null && result.length > 0) {
                System.out.println("PASS: encode with non-null extensionValue produces output") ;
            } else {
                System.out.println("FAIL: encode with non-null extensionValue produces no output") ;
            }
        } catch (Exception e) {
            System.out.println("FAIL: encode with non-null extensionValue threw exception: " + e);
        }
    }

    private static void testEncodeThrowsIOExceptionWhenExtensionIdNull() {
        try {
            CRLReasonCodeExtension ext = new CRLReasonCodeExtension(3); // reason code 3
            // forcibly set extensionId to null to simulate error
            ext.extensionId = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            System.out.println("FAIL: encode did not throw IOException when extensionId is null") ;
        } catch (IOException e) {
            System.out.println("PASS: encode threw IOException as expected when extensionId is null") ;
        } catch (Exception e) {
            System.out.println("FAIL: encode threw unexpected exception: " + e);
        }
    }
}
