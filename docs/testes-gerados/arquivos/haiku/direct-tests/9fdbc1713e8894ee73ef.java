/*
 * @test
 * @summary Verify DER encoding of ReasonFlags.encode(DerOutputStream), including truncation of trailing zero bits
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ReasonFlagsWitupTest
 */

import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.x509.ReasonFlags;

public class ReasonFlagsWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] keyCompromise = new byte[] {0x03, 0x02, 0x06, 0x40};

        check(new boolean[] {false, true}, keyCompromise);
        check(new boolean[] {false, true, false, false}, keyCompromise);
        check(new boolean[] {true}, new byte[] {0x03, 0x02, 0x07, (byte) 0x80});

        ReasonFlags rf = new ReasonFlags(new boolean[] {false, true});
        DerOutputStream first = new DerOutputStream();
        rf.encode(first);
        DerOutputStream second = new DerOutputStream();
        rf.encode(second);
        if (!Arrays.equals(first.toByteArray(), second.toByteArray())) {
            throw new RuntimeException("encode is not repeatable");
        }
        if (!Arrays.equals(first.toByteArray(), keyCompromise)) {
            throw new RuntimeException("unexpected encoding on repeated call");
        }
    }

    private static void check(boolean[] bits, byte[] expected) throws IOException {
        DerOutputStream out = new DerOutputStream();
        new ReasonFlags(bits).encode(out);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException("encoding mismatch: " + Arrays.toString(actual)
                    + " expected " + Arrays.toString(expected));
        }
    }
}
