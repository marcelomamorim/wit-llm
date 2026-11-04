/* @test    @summary Test encode method of ReasonFlags class    @run main ReasonFlagsWitupTest */

import java.io.IOException;
import sun.security.util.BitArray;
import sun.security.util.DerOutputStream;
import sun.security.x509.ReasonFlags;

public class ReasonFlagsWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodeWithAllFlags();
        testEncodeWithNoFlags();
        testEncodeWithSomeFlags();
    }

    private static void testEncodeWithAllFlags() throws IOException {
        ReasonFlags reasonFlags = new ReasonFlags();
        reasonFlags.setFlag("unused", true);
        reasonFlags.setFlag("key_compromise", true);
        reasonFlags.setFlag("ca_compromise", true);
        reasonFlags.setFlag("affiliation_changed", true);
        reasonFlags.setFlag("superseded", true);
        reasonFlags.setFlag("cessation_of_operation", true);
        reasonFlags.setFlag("certificate_hold", true);
        reasonFlags.setFlag("privilege_withdrawn", true);
        reasonFlags.setFlag("aa_compromise", true);
        DerOutputStream out = new DerOutputStream();
        reasonFlags.encode(out);
        BitArray bits = out.getBitString();
        boolean allSet = true;
        for (int i = 0; i < bits.length(); i++) {
            if (!bits.get(i)) {
                allSet = false;
                break;
            }
        }
        if (!allSet) {
            throw new AssertionError("All flags should be set in encode output");
        }
    }

    private static void testEncodeWithNoFlags() throws IOException {
        ReasonFlags reasonFlags = new ReasonFlags();
        DerOutputStream out = new DerOutputStream();
        reasonFlags.encode(out);
        BitArray bits = out.getBitString();
        for (int i = 0; i < bits.length(); i++) {
            if (bits.get(i)) {
                throw new AssertionError("No flags should be set in encode output");
            }
        }
    }

    private static void testEncodeWithSomeFlags() throws IOException {
        ReasonFlags reasonFlags = new ReasonFlags();
        reasonFlags.setFlag("key_compromise", true);
        reasonFlags.setFlag("superceded", true);
        DerOutputStream out = new DerOutputStream();
        reasonFlags.encode(out);
        BitArray bits = out.getBitString();
        if (!bits.get(1) || !bits.get(4)) {
            throw new AssertionError("Flags key_compromise and superseded should be set");
        }
        for (int i = 0; i < bits.length(); i++) {
            if (i != 1 && i != 4 && bits.get(i)) {
                throw new AssertionError("Unexpected flag set at position " + i);
            }
        }
    }
}
