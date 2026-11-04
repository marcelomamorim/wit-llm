/* @test    @summary Test for X400Address.subtreeDepth() method to ensure it throws UnsupportedOperationException.    @run main X400AddressWitupTest */

import sun.security.x509.X400Address;

public class X400AddressWitupTest {

    public static void main(String[] args) {
        testSubtreeDepthThrowsException();
    }

    public static void testSubtreeDepthThrowsException() {
        X400Address address = null;
        try {
            address = new X400Address(new byte[]{0x30, 0x00});
        } catch (Exception e) {
            // If construction fails, the test cannot proceed
            throw new RuntimeException("Failed to construct X400Address", e);
        }
        try {
            address.subtreeDepth();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
