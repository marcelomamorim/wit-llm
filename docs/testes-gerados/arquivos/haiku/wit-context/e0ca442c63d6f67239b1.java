/*
 * @test
 * @summary EDIPartyName.encode: DER round trip and rejection of null partyName
 * @modules java.base/sun.security.x509
 * @modules java.base/sun.security.util
 * @run main EDIPartyNameWitupTest
 */

import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.EDIPartyName;

public class EDIPartyNameWitupTest {

    public static void main(String[] args) throws Exception {
        testEncodePartyOnlyRoundTrip();
        testEncodeWithAssignerRoundTrip();
        testEncodeNullPartyThrows();
    }

    static byte[] encodeToBytes(EDIPartyName name) throws IOException {
        DerOutputStream out = new DerOutputStream();
        name.encode(out);
        return out.toByteArray();
    }

    static void testEncodePartyOnlyRoundTrip() throws IOException {
        EDIPartyName original = new EDIPartyName("party1");
        byte[] first = encodeToBytes(original);
        if (first == null || first.length == 0) {
            throw new RuntimeException("encode produced no output");
        }
        EDIPartyName decoded = new EDIPartyName(new DerValue(first));
        byte[] second = encodeToBytes(decoded);
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("round trip mismatch (party only)");
        }
    }

    static void testEncodeWithAssignerRoundTrip() throws IOException {
        EDIPartyName original = new EDIPartyName("assigner1", "party1");
        byte[] first = encodeToBytes(original);
        EDIPartyName decoded = new EDIPartyName(new DerValue(first));
        byte[] second = encodeToBytes(decoded);
        if (!Arrays.equals(first, second)) {
            throw new RuntimeException("round trip mismatch (with assigner)");
        }
    }

    static void testEncodeNullPartyThrows() {
        EDIPartyName name = new EDIPartyName("assigner1", null);
        try {
            encodeToBytes(name);
        } catch (IOException e) {
            return;
        }
        throw new RuntimeException("expected IOException for null partyName");
    }
}
