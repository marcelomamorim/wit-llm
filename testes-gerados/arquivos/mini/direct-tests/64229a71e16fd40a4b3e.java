/*
 * @test
 * @summary Test EDIPartyName.subtreeDepth throws UnsupportedOperationException
 * @run main EDIPartyNameWitupTest
 */

public class EDIPartyNameWitupTest {
    public static void main(String[] args) {
        sun.security.x509.EDIPartyName ediPartyName = new sun.security.x509.EDIPartyName("partyName");
        try {
            ediPartyName.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("subtreeDepth() not supported for EDIPartyName")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
