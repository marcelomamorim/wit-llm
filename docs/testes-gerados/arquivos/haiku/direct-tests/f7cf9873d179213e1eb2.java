/*
 * @test
 * @summary Checks argument validation and partial-buffer update behavior of MessageDigest.update(byte[], int, int)
 * @run main MessageDigestWitupTest
 */

import java.security.MessageDigest;
import java.util.Arrays;

public class MessageDigestWitupTest {

    public static void main(String[] args) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] abc = "abc".getBytes("US-ASCII");

        expectIllegalArgument(md, null, 0, 0);
        expectIllegalArgument(md, abc, 1, 3);
        expectIllegalArgument(md, abc, 4, 0);

        md.update(abc, 1, 2);
        byte[] partial = md.digest();
        byte[] expectedBc = MessageDigest.getInstance("SHA-256").digest("bc".getBytes("US-ASCII"));
        if (!Arrays.equals(partial, expectedBc)) {
            throw new RuntimeException("update(abc,1,2) digest mismatch");
        }

        md.update(abc, 0, 3);
        String hex = toHex(md.digest());
        if (!"ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad".equals(hex)) {
            throw new RuntimeException("update(abc,0,3) digest mismatch: " + hex);
        }

        md.update(abc, 3, 0);
        hex = toHex(md.digest());
        if (!"e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855".equals(hex)) {
            throw new RuntimeException("zero-length update at end mismatch: " + hex);
        }
    }

    private static void expectIllegalArgument(MessageDigest md, byte[] input, int offset, int len) {
        try {
            md.update(input, offset, len);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for offset=" + offset + " len=" + len);
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
