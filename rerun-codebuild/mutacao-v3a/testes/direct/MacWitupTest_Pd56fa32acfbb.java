/*
 * @test
 * @summary Test the update(ByteBuffer) method of javax.crypto.Mac for normal and exceptional behavior
 * @run main MacWitupTest_Pd56fa32acfbb
 */

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class MacWitupTest_Pd56fa32acfbb {

    public static void main(String[] args) throws Exception {
        testUpdateWithValidBuffer();
        testUpdateWithNullBuffer();
        testUpdateBeforeInit();
        testUpdateWithEmptyBuffer();
        testUpdateWithLargeBuffer();
    }

    static void testUpdateWithValidBuffer() throws Exception {
        Key key = new SecretKeySpec(new byte[] {1, 2, 3, 4}, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {10, 20, 30});
        mac.update(buffer);
        byte[] result = mac.doFinal();
        System.out.println("testUpdateWithValidBuffer passed, output length: " + result.length);
    }

    static void testUpdateWithNullBuffer() throws Exception {
        Key key = new SecretKeySpec(new byte[] {1}, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        try {
            mac.update((ByteBuffer) null);
            System.err.println("testUpdateWithNullBuffer failed: exception not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testUpdateWithNullBuffer passed");
        }
    }

    static void testUpdateBeforeInit() throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1});
        Mac mac = Mac.getInstance("HmacSHA256");
        try {
            mac.update(buffer);
            System.err.println("testUpdateBeforeInit failed: exception not thrown");
        } catch (IllegalStateException e) {
            System.out.println("testUpdateBeforeInit passed");
        }
    }

    static void testUpdateWithEmptyBuffer() throws Exception {
        Key key = new SecretKeySpec(new byte[] {1}, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {});
        mac.update(buffer);
        byte[] result = mac.doFinal();
        System.out.println("testUpdateWithEmptyBuffer passed, output length: " + result.length);
    }

    static void testUpdateWithLargeBuffer() throws Exception {
        Key key = new SecretKeySpec(new byte[] {1}, "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] largeBytes = new byte[1024 * 1024]; // 1MB
        for (int i = 0; i < largeBytes.length; i++) {
            largeBytes[i] = (byte) (i % 256);
        }
        ByteBuffer buffer = ByteBuffer.wrap(largeBytes);
        mac.update(buffer);
        byte[] result = mac.doFinal();
        System.out.println("testUpdateWithLargeBuffer passed, output length: " + result.length);
    }
}
