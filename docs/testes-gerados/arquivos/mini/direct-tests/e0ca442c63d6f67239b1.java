/*
 * @test
 * @summary Test sun.security.x509.EDIPartyName.encode method behavior
 * @run main sun.security.x509.EDIPartyNameWitupTest
 */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.EDIPartyName;

public class EDIPartyNameWitupTest {
    public static void main(String[] args) throws Exception {
        testEncodeWithAssignerAndParty();
        testEncodeWithOnlyParty();
        testEncodeWithNullPartyThrows();
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
        EDIPartyName edi = new EDIPartyName("partyOnly");
        DerOutputStream out = new DerOutputStream();
        edi.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded == null || encoded.length == 0) {
            throw new RuntimeException("Encoding failed: output is empty");
        }
    }

    private static void testEncodeWithNullPartyThrows() {
        EDIPartyName edi = new EDIPartyName((String) null, null);
        DerOutputStream out = new DerOutputStream();
        try {
            edi.encode(out);
            throw new RuntimeException("Expected IOException for null partyName not thrown");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("null partyName")) {
                throw new RuntimeException("IOException thrown but message unexpected: " + msg);
            }
        }
    }
}
