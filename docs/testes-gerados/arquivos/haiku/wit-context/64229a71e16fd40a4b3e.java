/*
 * @test
 * @summary Verify EDIPartyName.subtreeDepth() throws UnsupportedOperationException
 * @modules java.base/sun.security.x509
 * @run main EDIPartyNameWitupTest
 */
import sun.security.x509.EDIPartyName;

public class EDIPartyNameWitupTest {
    public static void main(String[] args) throws Exception {
        EDIPartyName partyOnly = new EDIPartyName("party");
        checkThrows(partyOnly);
        EDIPartyName withAssigner = new EDIPartyName("assigner", "party");
        checkThrows(withAssigner);
    }

    private static void checkThrows(EDIPartyName name) {
        try {
            int depth = name.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException, got depth " + depth);
        } catch (UnsupportedOperationException expected) {
        }
    }
}
