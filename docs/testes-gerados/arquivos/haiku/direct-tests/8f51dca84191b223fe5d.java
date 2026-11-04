/*
 * @test
 * @summary X400Address.subtreeDepth() must throw UnsupportedOperationException with its documented message
 * @modules java.base/sun.security.x509 java.base/sun.security.util
 * @run main X400AddressWitupTest
 */

import java.io.IOException;
import sun.security.util.DerValue;
import sun.security.x509.X400Address;

public class X400AddressWitupTest {

    private static final String EXPECTED_MESSAGE = "subtreeDepth not supported for X400Address";

    public static void main(String[] args) throws Exception {
        byte[] encoded = new byte[] {0x30, 0x00};

        X400Address fromBytes = new X400Address(encoded);
        checkSubtreeDepthThrows(fromBytes);

        DerValue derValue = new DerValue(encoded);
        X400Address fromDer = new X400Address(derValue);
        checkSubtreeDepthThrows(fromDer);
    }

    private static void checkSubtreeDepthThrows(X400Address address) {
        boolean thrown = false;
        try {
            address.subtreeDepth();
        } catch (UnsupportedOperationException e) {
            thrown = true;
            if (!EXPECTED_MESSAGE.equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("subtreeDepth() did not throw UnsupportedOperationException");
        }
    }
}
