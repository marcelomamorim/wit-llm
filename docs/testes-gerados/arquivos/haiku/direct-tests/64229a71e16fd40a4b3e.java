/*
 * @test
 * @summary EDIPartyName.subtreeDepth must throw UnsupportedOperationException with the documented message
 * @modules java.base/sun.security.x509
 * @run main EDIPartyNameWitupTest
 */

import sun.security.x509.EDIPartyName;

public class EDIPartyNameWitupTest {

    public static void main(String[] args) throws Exception {
        check(new EDIPartyName("party"));
        check(new EDIPartyName("assigner", "party"));
        check(new EDIPartyName(null, null));
    }

    private static void check(EDIPartyName name) {
        try {
            int depth = name.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException but got depth " + depth);
        } catch (UnsupportedOperationException e) {
            String expected = "subtreeDepth() not supported for EDIPartyName";
            if (!expected.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
