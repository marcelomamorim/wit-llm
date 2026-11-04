/* @test    @summary Test for IPAddressName.subtreeDepth() method to ensure it throws UnsupportedOperationException.    @run main IPAddressNameWitupTest */

import sun.security.x509.IPAddressName;

public class IPAddressNameWitupTest {
    public static void main(String[] args) {
        try {
            IPAddressName ipName = new IPAddressName(new byte[] {1, 2, 3, 4});
            ipName.subtreeDepth();
            System.out.println("Test failed: no exception thrown.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Test passed: UnsupportedOperationException thrown as expected.");
        } catch (Exception e) {
            System.out.println("Test failed: unexpected exception: " + e);
        }
    }
}
