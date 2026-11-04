/* @test    @summary Test for X500Principal constructor with null name to verify NullPointerException.    @run main X500PrincipalWitupTest */

import javax.security.auth.x500.X500Principal;

public class X500PrincipalWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullName();
    }

    public static void testConstructorWithNullName() {
        try {
            new X500Principal((String) null);
            System.err.println("Expected NullPointerException was not thrown.");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception, test passes
            System.out.println("NullPointerException thrown as expected.");
        } catch (Throwable t) {
            System.err.println("Unexpected exception type: " + t);
            System.exit(1);
        }
    }
}
