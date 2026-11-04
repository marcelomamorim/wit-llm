/* @test    @summary Test for getPassword method in PBEKeySpec to verify behavior when password is null    @run main PBEKeySpecWitupTest */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        testGetPasswordWithNullPassword();
        testGetPasswordWithValidPassword();
    }

    private static void testGetPasswordWithNullPassword() {
        PBEKeySpec spec = new PBEKeySpec(new char[0]);
        try {
            // Simulate clearing the password to null to trigger exception
            // Note: Since the constructor clones the array, we can set the internal password to null via reflection or by subclassing, but as reflection is discouraged, we simulate by creating a subclass.
            PBEKeySpec specWithNull = new PBEKeySpec(new char[0]) {
                {
                    // forcibly set password to null
                    this.password = null;
                }
            };
            specWithNull.getPassword();
            // If no exception, test fails
            System.err.println("FAIL: Expected IllegalStateException when password is null");
        } catch (IllegalStateException e) {
            // Expected exception
            System.out.println("PASS: getPassword throws IllegalStateException when password is null");
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testGetPasswordWithValidPassword() {
        char[] pwd = {'a', 'b', 'c'};
        PBEKeySpec spec = new PBEKeySpec(pwd);
        try {
            char[] result = spec.getPassword();
            if (result != null && result.length == pwd.length) {
                boolean match = true;
                for (int i = 0; i < pwd.length; i++) {
                    if (result[i] != pwd[i]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    System.out.println("PASS: getPassword returns a clone with correct content");
                } else {
                    System.err.println("FAIL: getPassword clone content mismatch");
                }
            } else {
                System.err.println("FAIL: getPassword returned null or incorrect length");
            }
        } catch (Exception e) {
            System.err.println("FAIL: Unexpected exception: " + e);
        }
    }
}
