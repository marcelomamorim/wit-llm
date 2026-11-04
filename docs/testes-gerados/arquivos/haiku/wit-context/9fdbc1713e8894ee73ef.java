/*
 * @test
 * @summary Witup test for ReasonFlags.encode DER bit string output
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main ReasonFlagsWitupTest
 */

import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.x509.ReasonFlags;

public class ReasonFlagsWitupTest {

    public static void main(String[] args) throws Exception {
        check(new boolean[]{false, true},
              new byte[]{0x03, 0x02, 0x06, 0x40});
        check(new boolean[]{true},
              new byte[]{0x03, 0x02, 0x07, (byte) 0x80});
        check(new boolean[]{false, false, false, false, false, false, false, false, true},
              new byte[]{0x03, 0x03, 0x07, 0x00, (byte) 0x80});
        checkRepeatable(new boolean[]{false, true, true});
    }

    private static void check(boolean[] bits, byte[] expected) throws Exception {
        ReasonFlags rf = new ReasonFlags(bits);
        DerOutputStream out = new DerOutputStream();
        rf.encode(out);
        byte[] actual = out.toByteArray();
        if (!Arrays.equals(actual, expected)) {
            throw new RuntimeException("Unexpected DER encoding: "
                    + Arrays.toString(actual) + " expected "
                    + Arrays.toString(expected));
        }
    }

    private static void checkRepeatable(boolean[] bits) throws Exception {
        ReasonFlags rf = new ReasonFlags(bits);
        DerOutputStream first = new DerOutputStream();
        DerOutputStream second = new DerOutputStream();
        rf.encode(first);
        rf.encode(second);
        if (!Arrays.equals(first.toByteArray(), second.toByteArray())) {
            throw new RuntimeException("Encoding is not repeatable");
        }
    }
}
