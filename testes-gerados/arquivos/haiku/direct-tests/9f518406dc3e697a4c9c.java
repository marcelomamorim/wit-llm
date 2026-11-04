/*
 * @test
 * @summary Verify MessageDigest.update(ByteBuffer) null handling and equivalence with byte[] updates
 * @run main MessageDigestWitupTest
 */

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;

public class MessageDigestWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNPE();
        testByteBufferMatchesByteArrayUpdate();
        testKnownSha256Vector();
        testSplitBufferUpdatesMatchSingleUpdate();
        testEmptyBufferDoesNotChangeDigest();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    static void testNullInputThrowsNPE() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        boolean thrown = false;
        try {
            md.update((ByteBuffer) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "update(null ByteBuffer) must throw NullPointerException");
    }

    static void testByteBufferMatchesByteArrayUpdate() throws Exception {
        byte[] data = "abc".getBytes("UTF-8");

        MessageDigest mdBuffer = MessageDigest.getInstance("SHA-256");
        ByteBuffer bb = ByteBuffer.wrap(data);
        mdBuffer.update(bb);
        byte[] fromBuffer = mdBuffer.digest();

        MessageDigest mdArray = MessageDigest.getInstance("SHA-256");
        mdArray.update(data);
        byte[] fromArray = mdArray.digest();

        check(Arrays.equals(fromBuffer, fromArray),
              "ByteBuffer update must produce same digest as byte[] update");
    }

    static void testKnownSha256Vector() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(ByteBuffer.wrap("abc".getBytes("UTF-8")));
        String hex = toHex(md.digest());
        check("ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad".equals(hex),
              "Unexpected SHA-256 digest: " + hex);
    }

    static void testSplitBufferUpdatesMatchSingleUpdate() throws Exception {
        MessageDigest split = MessageDigest.getInstance("SHA-256");
        split.update(ByteBuffer.wrap("ab".getBytes("UTF-8")));
        split.update(ByteBuffer.wrap("c".getBytes("UTF-8")));
        byte[] splitDigest = split.digest();

        MessageDigest single = MessageDigest.getInstance("SHA-256");
        single.update(ByteBuffer.wrap("abc".getBytes("UTF-8")));
        byte[] singleDigest = single.digest();

        check(Arrays.equals(splitDigest, singleDigest),
              "Split ByteBuffer updates must equal a single update");
    }

    static void testEmptyBufferDoesNotChangeDigest() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(ByteBuffer.allocate(0));
        byte[] afterEmpty = md.digest();

        MessageDigest fresh = MessageDigest.getInstance("SHA-256");
        byte[] emptyDigest = fresh.digest();

        check(Arrays.equals(afterEmpty, emptyDigest),
              "Updating with an empty ByteBuffer must equal digest of empty input");
    }
}
