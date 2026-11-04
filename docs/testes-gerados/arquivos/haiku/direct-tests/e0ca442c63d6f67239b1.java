/*
 * @test
 * @summary EDIPartyName.encode produces the expected DER layout, round-trips, and rejects a null partyName
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main EDIPartyNameWitupTest
 */
import java.io.IOException;
import java.util.Arrays;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.EDIPartyName;

public class EDIPartyNameWitupTest {

    public static void main(String[] args) throws Exception {
        testPartyOnlyEncoding();
        testAssignerAndPartyRoundTrip();
        testNullPartyThrows();
    }

    static byte[] enc(EDIPartyName n) throws IOException {
        DerOutputStream out = new DerOutputStream();
        n.encode(out);
        return out.toByteArray();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testPartyOnlyEncoding() throws Exception {
        // SEQUENCE { [1] PrintableString "A" }
        byte[] expected = {0x30, 0x05, (byte) 0x81, 0x03, 0x13, 0x01, 0x41};
        byte[] actual = enc(new EDIPartyName("A"));
        check(Arrays.equals(expected, actual),
              "unexpected encoding for party-only name: " + Arrays.toString(actual));
    }

    static void testAssignerAndPartyRoundTrip() throws Exception {
        byte[] first = enc(new EDIPartyName("B", "A"));
        check(first.length > 0 && first[0] == 0x30, "encoding must start with SEQUENCE tag");
        // assigner [0] primitive tag is 0x80
        check(first[2] == (byte) 0x80, "nameAssigner must be encoded with context tag 0");
        byte[] second = enc(new EDIPartyName(new DerValue(first)));
        check(Arrays.equals(first, second), "re-encoding decoded value changed bytes");
    }

    static void testNullPartyThrows() {
        try {
            enc(new EDIPartyName((String) null));
            throw new RuntimeException("expected IOException for null partyName");
        } catch (IOException expected) {
            // expected
        }
    }
}
