/* @test    @summary Test constructor ReasonFlags(DerValue derVal) for ReasonFlags class    @run main ReasonFlagsWitupTest */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.ReasonFlags;

public class ReasonFlagsWitupTest {

    public static void main(String[] args) throws IOException {
        testReasonFlagsConstructor();
    }

    private static void testReasonFlagsConstructor() throws IOException {
        // Create a DER-encoded BIT STRING with specific bits set (e.g., bits 1 and 3)
        byte[] bitStringBytes = new byte[] { (byte) 0b00001010 }; // bits 1 and 3 set
        DerValue derValue = new DerValue(bitStringBytes);
        // Instantiate ReasonFlags with the DER value
        ReasonFlags reasonFlags = new ReasonFlags(derValue);
        // Verify that the bits are correctly interpreted
        boolean keyCompromise = reasonFlags.isSet(1); // should be true
        boolean affiliationChanged = reasonFlags.isSet(3); // should be true
        boolean unused = reasonFlags.isSet(0); // should be false
        // Output results
        System.out.println("key_compromise: " + keyCompromise);
        System.out.println("affiliation_changed: " + affiliationChanged);
        System.out.println("unused: " + unused);
        // Basic assertions
        if (!keyCompromise || !affiliationChanged || unused) {
            throw new AssertionError("ReasonFlags constructor did not parse bits correctly.");
        }
    }
}
