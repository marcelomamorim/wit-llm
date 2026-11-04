/* @test    @summary Test encoding of PrivateKeyUsageExtension.    @run main PrivateKeyUsageExtensionWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import sun.security.x509.PrivateKeyUsageExtension;

public class PrivateKeyUsageExtensionWitupTest {

    public static void main(String[] args) {
        testEncodeWithNullValues();
        testEncodeWithNotBefore();
        testEncodeWithNotAfter();
        testEncodeWithBothDates();
    }

    static void testEncodeWithNullValues() {
        try {
            PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            // Expect minimal encoding with no extension value
            if (result.length == 0) {
                System.out.println("PASS: encode with null values");
            } else {
                System.out.println("FAIL: encode with null values, output length: " + result.length);
            }
        } catch (Exception e) {
            System.out.println("FAIL: encode with null values, exception: " + e);
        }
    }

    static void testEncodeWithNotBefore() {
        try {
            PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension();
            Date notBefore = new Date(1630454400000L); // 2021-09-01T00:00:00Z
            ext.set("not_before", notBefore);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("PASS: encode with not_before");
            } else {
                System.out.println("FAIL: encode with not_before, output empty");
            }
        } catch (Exception e) {
            System.out.println("FAIL: encode with not_before, exception: " + e);
        }
    }

    static void testEncodeWithNotAfter() {
        try {
            PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension();
            Date notAfter = new Date(1640995200000L); // 2022-01-01T00:00:00Z
            ext.set("not_after", notAfter);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("PASS: encode with not_after");
            } else {
                System.out.println("FAIL: encode with not_after, output empty");
            }
        } catch (Exception e) {
            System.out.println("FAIL: encode with not_after, exception: " + e);
        }
    }

    static void testEncodeWithBothDates() {
        try {
            PrivateKeyUsageExtension ext = new PrivateKeyUsageExtension();
            Date notBefore = new Date(1630454400000L); // 2021-09-01T00:00:00Z
            Date notAfter = new Date(1640995200000L); // 2022-01-01T00:00:00Z
            ext.set("not_before", notBefore);
            ext.set("not_after", notAfter);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ext.encode(baos);
            byte[] result = baos.toByteArray();
            if (result.length > 0) {
                System.out.println("PASS: encode with both dates");
            } else {
                System.out.println("FAIL: encode with both dates, output empty");
            }
        } catch (Exception e) {
            System.out.println("FAIL: encode with both dates, exception: " + e);
        }
    }
}
