/*
 * @test
 * @summary Test subtreeDepth method of sun.security.x509.X400Address throws UnsupportedOperationException
 * @run main X400AddressWitupTest
 */

import sun.security.x509.X400Address;

public class X400AddressWitupTest {
    public static void main(String[] args) {
        // Prepare minimal valid input byte array for constructor
        // Since the internal structure is complex and not public,
        // use a minimal non-null byte array to instantiate.
        byte[] dummyValue = new byte[] {0x30, 0x00}; // DER SEQUENCE with length 0

        X400Address x400Address = new X400Address(dummyValue);

        try {
            x400Address.subtreeDepth();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }
}
