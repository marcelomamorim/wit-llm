/*
 * @test
 * @summary Verifies that UniqueIdentity(DerInputStream) rejects a null stream with NullPointerException
 * @modules java.base/sun.security.util java.base/sun.security.x509
 * @run main UniqueIdentityWitupTest
 */

import sun.security.util.DerInputStream;
import sun.security.x509.UniqueIdentity;

public class UniqueIdentityWitupTest {
    public static void main(String[] args) throws Exception {
        boolean thrown = false;
        try {
            new UniqueIdentity((DerInputStream) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null DerInputStream");
        }
    }
}
