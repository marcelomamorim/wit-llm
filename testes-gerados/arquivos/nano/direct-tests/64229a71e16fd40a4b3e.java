/* @test    @summary Test for EDIPartyName.subtreeDepth() method to ensure it throws UnsupportedOperationException as specified.    @run main EDIPartyNameWitupTest */

import sun.security.x509.EDIPartyName;

public class EDIPartyNameWitupTest {

    public static void main(String[] args) {
        testSubtreeDepthThrowsException();
    }

    public static void testSubtreeDepthThrowsException() {
        EDIPartyName edip = new EDIPartyName("assigner", "party");
        try {
            edip.subtreeDepth();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes.
            System.out.println("subtreeDepth() correctly threw UnsupportedOperationException.");
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
