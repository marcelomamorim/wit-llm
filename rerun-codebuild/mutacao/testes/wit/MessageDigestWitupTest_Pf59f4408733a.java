/* @test    @summary Test for MessageDigest.update(ByteBuffer) method handling null input and normal operation    @run main MessageDigestWitupTest_Pf59f4408733a */

import java.security.MessageDigest;
import java.nio.ByteBuffer;

public class MessageDigestWitupTest_Pf59f4408733a {

    public static void main(String[] args) {
        testUpdateWithNullInput();
        testUpdateWithValidInput();
    }

    private static void testUpdateWithNullInput() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((ByteBuffer) null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testUpdateWithValidInput() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] data = {1, 2, 3, 4, 5};
            ByteBuffer buffer = ByteBuffer.wrap(data);
            md.update(buffer);
            byte[] digest = md.digest();
            if (digest != null && digest.length > 0) {
                System.out.println("PASS: digest computed successfully");
            } else {
                System.out.println("FAIL: digest is null or empty");
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
