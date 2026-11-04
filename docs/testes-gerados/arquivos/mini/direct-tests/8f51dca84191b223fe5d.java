/*
 * @test
 * @summary Test subtreeDepth method of X400Address throws UnsupportedOperationException
 * @run main X400AddressWitupTest
 */

import sun.security.x509.X400Address;

public class X400AddressWitupTest {
    public static void main(String[] args) {
        testSubtreeDepthThrows();
    }

    private static void testSubtreeDepthThrows() {
        byte[] dummy = new byte[] {0x30, 0x00}; // minimal DER sequence
        X400Address addr = new X400Address(dummy);
        try {
            addr.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("subtreeDepth not supported")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }
}
