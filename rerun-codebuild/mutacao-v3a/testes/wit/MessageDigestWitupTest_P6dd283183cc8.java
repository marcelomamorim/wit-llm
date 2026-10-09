/* @test    @summary Test digest(byte[], int, int) method for null buffer and small buffer exceptions    @run main MessageDigestWitupTest_P6dd283183cc8 */

import java.security.*;

public class MessageDigestWitupTest_P6dd283183cc8 {

    public static void main(String[] args) throws Exception {
        testDigestNullBuffer();
        testDigestSmallBuffer();
    }

    private static void testDigestNullBuffer() {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] buffer = null;
        try {
            md.digest(buffer, 0, 10);
            System.err.println("Failed: Expected IllegalArgumentException for null buffer");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    private static void testDigestSmallBuffer() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[5]; // smaller than required for len=10
        try {
            md.digest(buffer, 0, 10);
            System.err.println("Failed: Expected IllegalArgumentException for small buffer");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
