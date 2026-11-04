/* @test    @summary Test for sun.security.x509.EDIPartyName.subtreeDepth() method
 * @run main EDIPartyNameWitupTest
 */

import sun.security.x509.EDIPartyName;

public class EDIPartyNameWitupTest {

    public static void main(String[] args) {
        testSubtreeDepthThrowsException();
    }

    public static void testSubtreeDepthThrowsException() {
        EDIPartyName instance = new EDIPartyName("PartyName");
        try {
            int result = instance.subtreeDepth();
            // If no exception, test fails
            System.out.println("FAIL: Expected UnsupportedOperationException, but got result: " + result);
        } catch (UnsupportedOperationException e) {
            // Expected exception
            System.out.println("PASS: Caught expected UnsupportedOperationException");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }
}
