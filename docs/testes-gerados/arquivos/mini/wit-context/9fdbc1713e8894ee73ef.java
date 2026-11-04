/*
 * @test
 * @summary Test sun.security.x509.ReasonFlags.encode(DerOutputStream) for normal and edge cases
 * @run main ReasonFlagsWitupTest
 */

import sun.security.x509.ReasonFlags;
import sun.security.util.DerOutputStream;
import sun.security.util.BitArray;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReasonFlagsWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeNormal();
        testEncodeEmptyBitString();
    }

    private static void testEncodeNormal() throws IOException {
        // Create ReasonFlags with some bits set
        boolean[] bits = new boolean[9];
        bits[0] = true;  // unused
        bits[1] = true;  // key_compromise
        bits[8] = true;  // aa_compromise
        ReasonFlags rf = new ReasonFlags(bits);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        rf.encode(derOut);
        derOut.flush();

        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded output is empty");
        }
    }

    private static void testEncodeEmptyBitString() throws IOException {
        // Create ReasonFlags with no bits set
        boolean[] bits = new boolean[0];
        ReasonFlags rf = new ReasonFlags(bits);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream derOut = new DerOutputStream(baos);

        rf.encode(derOut);
        derOut.flush();

        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoded output is empty for empty bit string");
        }
    }
}
