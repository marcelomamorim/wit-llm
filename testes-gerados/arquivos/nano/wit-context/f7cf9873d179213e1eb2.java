/* @test    @summary Test for MessageDigest.update(byte[], int, int) handling null and short buffer inputs    @run main MessageDigestWitupTest */

import java.security.MessageDigest;

public class MessageDigestWitupTest {

    public static void main(String[] args) {
        testUpdateNullInput();
        testUpdateShortBuffer();
    }

    private static void testUpdateNullInput() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(null, 0, 1);
            System.out.println("FAIL: Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null input");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testUpdateShortBuffer() {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte[] input = {0, 1, 2};
            int offset = 1;
            int len = 3; // intentionally too long
            md.update(input, offset, len);
            System.out.println("FAIL: Expected IllegalArgumentException for short buffer");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for short buffer");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
