/*
 * @test
 * @summary Test X500Principal(String name) constructor for null and valid inputs
 * @run main X500PrincipalWitupTest
 */

import javax.security.auth.x500.X500Principal;
import java.util.Collections;

public class X500PrincipalWitupTest {
    public static void main(String[] args) throws Exception {
        testNullNameThrowsNPE();
        testValidNameCreatesInstance();
    }

    private static void testNullNameThrowsNPE() {
        try {
            new X500Principal((String) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testValidNameCreatesInstance() {
        String dn = "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US";
        X500Principal p = new X500Principal(dn);
        if (p == null) {
            throw new RuntimeException("X500Principal instance is null");
        }
        String name = p.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("X500Principal.getName() returned null or empty string");
        }
        // The returned name should contain the CN component
        if (!name.contains("CN=Duke")) {
            throw new RuntimeException("X500Principal.getName() does not contain expected CN component");
        }
    }
}
