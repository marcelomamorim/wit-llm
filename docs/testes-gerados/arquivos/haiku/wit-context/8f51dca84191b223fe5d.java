/*
 * @test
 * @summary Verify that X400Address.subtreeDepth() always throws UnsupportedOperationException with the documented message
 * @modules java.base/sun.security.x509
 * @run main X400AddressWitupTest
 */

public class X400AddressWitupTest {
    public static void main(String[] args) throws Exception {
        X400Address addr = new X400Address(new byte[] {0x30, 0x00});
        boolean thrown = false;
        String message = null;
        try {
            addr.subtreeDepth();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            message = e.getMessage();
        }
        if (!thrown) {
            throw new RuntimeException("Expected UnsupportedOperationException from subtreeDepth()");
        }
        if (!"subtreeDepth not supported for X400Address".equals(message)) {
            throw new RuntimeException("Unexpected exception message: " + message);
        }
    }
}
