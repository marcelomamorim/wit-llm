/* @test    @summary Test ReasonFlags.encode() method for normal and exceptional behavior    @run main ReasonFlagsWitupTest */

import java.io.IOException;
import sun.security.util.BitArray;
import sun.security.util.DerOutputStream;
import sun.security.x509.ReasonFlags;

public class ReasonFlagsWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeNormal();
        testEncodeOutOfMemoryError();
    }

    private static void testEncodeNormal() throws IOException {
        ReasonFlags rf = new ReasonFlags(new boolean[] {true, false, true, false, true, false, true, false, true});
        DerOutputStream out = new DerOutputStream();
        rf.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new AssertionError("Encoding returned null or empty byte array");
        }
        System.out.println("testEncodeNormal passed.");
    }

    private static void testEncodeOutOfMemoryError() {
        ReasonFlags rf = new ReasonFlags(new boolean[] {true, false, true, false, true, false, true, false, true});
        DerOutputStream out = new DerOutputStream() {
            @Override
            public void putTruncatedUnalignedBitString(BitArray bits) throws IOException {
                throw new OutOfMemoryError("Simulated OutOfMemoryError for testing");
            }
        };
        try {
            rf.encode(out);
            throw new AssertionError("Expected OutOfMemoryError was not thrown");
        } catch (OutOfMemoryError e) {
            System.out.println("testEncodeOutOfMemoryError passed.");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
