/* @test    @summary Test for MessageDigest.digest(byte[], int, int) handling null buffer and small buffer cases    @run main MessageDigestWitupTest_P6177eb0716aa */

import java.security.*;

public class MessageDigestWitupTest_P6177eb0716aa {

    public static void main(String[] args) {
        testDigestNullBuffer();
        testDigestSmallBuffer();
    }

    private static void testDigestNullBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = null;
            md.digest(buffer, 0, 10);
            System.out.println("FAIL: Expected IllegalArgumentException for null buffer");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null buffer");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testDigestSmallBuffer() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[5];
            int offset = 0;
            int len = 10;
            md.digest(buffer, offset, len);
            System.out.println("FAIL: Expected IllegalArgumentException for small buffer");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for small buffer");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
