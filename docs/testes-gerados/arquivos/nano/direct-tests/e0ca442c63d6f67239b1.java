/* @test    @summary Test encoding of EDIPartyName's encode method.    @run main EDIPartyNameWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.x509.EDIPartyName;

public class EDIPartyNameWitupTest {

    public static void main(String[] args) throws IOException {
        testEncodeWithAssignerAndParty();
        testEncodeWithOnlyParty();
        testEncodeNullPartyThrows();
    }

    static void testEncodeWithAssignerAndParty() throws IOException {
        EDIPartyName edip = new EDIPartyName("Assign", "Party");
        DerOutputStream out = new DerOutputStream();
        edip.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded.length == 0) {
            throw new AssertionError("Encoding resulted in empty byte array");
        }
        System.out.println("testEncodeWithAssignerAndParty passed.");
    }

    static void testEncodeWithOnlyParty() throws IOException {
        EDIPartyName edip = new EDIPartyName("PartyOnly");
        DerOutputStream out = new DerOutputStream();
        edip.encode(out);
        byte[] encoded = out.toByteArray();
        if (encoded.length == 0) {
            throw new AssertionError("Encoding resulted in empty byte array");
        }
        System.out.println("testEncodeWithOnlyParty passed.");
    }

    static void testEncodeNullPartyThrows() {
        try {
            EDIPartyName edip = new EDIPartyName(null);
            DerOutputStream out = new DerOutputStream();
            edip.encode(out);
            throw new AssertionError("Expected IOException for null party");
        } catch (IOException e) {
            System.out.println("testEncodeNullPartyThrows passed.");
        }
    }
}
