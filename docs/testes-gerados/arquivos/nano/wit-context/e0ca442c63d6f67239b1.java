/* @test    @summary Test for sun.security.x509.EDIPartyName.encode method    @run main EDIPartyNameWitupTest */

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

public class EDIPartyNameWitupTest {

    public static void main(String[] args) {
        testEncodeWithNullParty();
        testEncodeWithNullAssignorAndParty();
        testEncodeWithValidAssignorAndParty();
    }

    private static void testEncodeWithNullParty() {
        try {
            sun.security.x509.EDIPartyName edip = new sun.security.x509.EDIPartyName("assignerName", null);
            DerOutputStream out = new DerOutputStream();
            edip.encode(out);
            System.out.println("testEncodeWithNullParty: Passed");
        } catch (IOException e) {
            System.out.println("testEncodeWithNullParty: Failed with IOException: " + e.getMessage());
        }
    }

    private static void testEncodeWithNullAssignorAndParty() {
        try {
            sun.security.x509.EDIPartyName edip = new sun.security.x509.EDIPartyName(null, null);
            DerOutputStream out = new DerOutputStream();
            edip.encode(out);
            System.out.println("testEncodeWithNullAssignorAndParty: Failed - expected IOException");
        } catch (IOException e) {
            if (e.getMessage().contains("Cannot have null partyName")) {
                System.out.println("testEncodeWithNullAssignorAndParty: Passed");
            } else {
                System.out.println("testEncodeWithNullAssignorAndParty: Failed with unexpected IOException: " + e.getMessage());
            }
        }
    }

    private static void testEncodeWithValidAssignorAndParty() {
        try {
            sun.security.x509.EDIPartyName edip = new sun.security.x509.EDIPartyName("assigner", "party");
            DerOutputStream out = new DerOutputStream();
            edip.encode(out);
            byte[] encoded = out.toByteArray();
            if (encoded != null && encoded.length > 0) {
                System.out.println("testEncodeWithValidAssignorAndParty: Passed");
            } else {
                System.out.println("testEncodeWithValidAssignorAndParty: Failed - empty encoding");
            }
        } catch (IOException e) {
            System.out.println("testEncodeWithValidAssignorAndParty: Failed with IOException: " + e.getMessage());
        }
    }
}
