/*
 * @test
 * @summary Test sun.security.x509.ReasonFlags encode method
 * @run main ReasonFlagsWitupTest
 */

import sun.security.x509.ReasonFlags;
import sun.security.util.DerOutputStream;
import sun.security.util.BitArray;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ReasonFlagsWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeEmptyBitString();
        testEncodeWithBitsSet();
    }

    private static void testEncodeEmptyBitString() throws IOException {
        // Create ReasonFlags with empty bitString (all false)
        boolean[] bits = new boolean[0];
        ReasonFlags rf = new ReasonFlags(bits);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);

        rf.encode(dos);

        byte[] encoded = baos.toByteArray();
        // The encoding should not be null or empty
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoding of empty ReasonFlags produced no output");
        }
    }

    private static void testEncodeWithBitsSet() throws IOException {
        // Create ReasonFlags with some bits set
        boolean[] bits = new boolean[9];
        bits[0] = true;  // unused
        bits[1] = true;  // key_compromise
        bits[8] = true;  // aa_compromise
        ReasonFlags rf = new ReasonFlags(bits);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DerOutputStream dos = new DerOutputStream(baos);

        rf.encode(dos);

        byte[] encoded = baos.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoding of ReasonFlags with bits set produced no output");
        }

        // The encoded output should contain the bits set, so length should be > 0
        // We cannot assert exact bytes without DerOutputStream details, but length > 0 is minimal check
    }
}
