/*
 * @test
 * @summary Test sun.security.x509.EDIPartyName.encode(DerOutputStream) for correct encoding and exception on null party
 * @run main EDIPartyNameWitupTest
 */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.EDIPartyName;

public class EDIPartyNameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithAssignerAndParty();
        testEncodeWithOnlyParty();
        testEncodeThrowsOnNullParty();
    }

    private static void testEncodeWithAssignerAndParty() throws IOException {
        EDIPartyName edi = new EDIPartyName("assignerName", "partyName");
        DerOutputStream out = new DerOutputStream();
        edi.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoding failed: output is empty");
        }
    }

    private static void testEncodeWithOnlyParty() throws IOException {
        EDIPartyName edi = new EDIPartyName("partyNameOnly");
        DerOutputStream out = new DerOutputStream();
        edi.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoding failed: output is empty");
        }
    }

    private static void testEncodeThrowsOnNullParty() {
        EDIPartyName edi = new EDIPartyName((String) null, null);
        DerOutputStream out = new DerOutputStream();
        try {
            edi.encode(out);
            throw new RuntimeException("Expected IOException not thrown when party is null");
        } catch (IOException e) {
            // Expected exception
        }
    }
}
