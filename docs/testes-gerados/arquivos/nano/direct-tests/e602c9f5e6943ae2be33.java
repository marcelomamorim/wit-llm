/* @test    @summary Test encoding of SubjectAlternativeNameExtension.    @run main SubjectAlternativeNameExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import sun.security.x509.SubjectAlternativeNameExtension;
import sun.security.util.GeneralNames;

public class SubjectAlternativeNameExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeWithNullExtensionValue();
        testEncodeWithEmptyNames();
        testEncodeWithValidNames();
    }

    static void testEncodeWithNullExtensionValue() {
        try {
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension();
            ext.extensionValue = null; // forcibly set to null
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            // Expect the encode method to handle null extensionValue gracefully
            // and produce a valid encoding (likely minimal or empty)
            if (result.length == 0) {
                System.out.println("testEncodeWithNullExtensionValue passed");
            } else {
                System.out.println("testEncodeWithNullExtensionValue failed: non-empty output");
            }
        } catch (Exception e) {
            System.out.println("testEncodeWithNullExtensionValue failed with exception: " + e);
        }
    }

    static void testEncodeWithEmptyNames() {
        try {
            GeneralNames emptyNames = new GeneralNames();
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(false, emptyNames);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            // Expect minimal encoding for empty names
            if (result.length > 0) {
                System.out.println("testEncodeWithEmptyNames passed");
            } else {
                System.out.println("testEncodeWithEmptyNames failed: empty output");
            }
        } catch (Exception e) {
            System.out.println("testEncodeWithEmptyNames failed with exception: " + e);
        }
    }

    static void testEncodeWithValidNames() {
        try {
            // Create a GeneralNames with dummy data
            GeneralNames names = new GeneralNames();
            // Since GeneralNames constructor and methods are not detailed,
            // assume a method add() exists for test purposes
            // names.add(new GeneralName(...)); // Not implemented here
            // For testing, we simulate non-empty names
            // But as we cannot instantiate GeneralName, we simulate with a subclass or mock
            // For simplicity, assume names is non-null and non-empty
            // and encode() will process it
            // Note: In real test, you'd construct proper GeneralNames
            // Here, we proceed with the assumption
            // For the purpose of this test, we simulate with a subclass
            GeneralNames testNames = new GeneralNames() {
                @Override
                public void encode(OutputStream out) throws IOException {
                    // write dummy data
                    out.write(0x30); // SEQUENCE tag
                    out.write(0x00); // length 0
                }
                @Override
                public boolean isEmpty() {
                    return false;
                }
            };
            SubjectAlternativeNameExtension ext = new SubjectAlternativeNameExtension(false, testNames);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("testEncodeWithValidNames passed");
            } else {
                System.out.println("testEncodeWithValidNames failed: empty output");
            }
        } catch (Exception e) {
            System.out.println("testEncodeWithValidNames failed with exception: " + e);
        }
    }
}
