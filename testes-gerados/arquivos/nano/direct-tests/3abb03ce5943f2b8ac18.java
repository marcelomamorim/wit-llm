/* @test    @summary Test constructor X500Principal(String) with valid and null input    @run main X500PrincipalWitupTest */

import javax.security.auth.x500.X500Principal;
import java.util.Collections;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        testValidName();
        testNullName();
    }

    static void testValidName() {
        String name = "CN=Duke, OU=JavaSoft, O=Sun Microsystems, C=US";
        X500Principal principal = new X500Principal(name);
        assert principal != null : "Principal should not be null for valid name";
        assert principal.getName().equals(name) : "Principal name mismatch";
    }

    static void testNullName() {
        try {
            X500Principal principal = new X500Principal((String) null);
            assert false : "Expected NullPointerException for null name";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
