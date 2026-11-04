/*
 * @test
 * @summary Test sun.security.x509.EDIPartyName.subtreeDepth() throws UnsupportedOperationException
 * @run main EDIPartyNameWitupTest
 */

public class EDIPartyNameWitupTest {
    public static void main(String[] args) {
        EDIPartyName ediPartyName = new EDIPartyName("partyName");
        try {
            ediPartyName.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("subtreeDepth() not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
