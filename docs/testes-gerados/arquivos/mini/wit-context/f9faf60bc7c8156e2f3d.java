/*
 * @test
 * @summary Test javax.crypto.spec.PBEKeySpec.getPassword() behavior including exception on cleared password
 * @run main PBEKeySpecWitupTest
 */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testGetPasswordReturnsClone();
        testGetPasswordThrowsAfterClear();
    }

    private static void testGetPasswordReturnsClone() {
        char[] original = { 'p', 'a', 's', 's' };
        PBEKeySpec spec = new PBEKeySpec(original);
        char[] pwd1 = spec.getPassword();
        char[] pwd2 = spec.getPassword();

        if (pwd1 == null) {
            throw new RuntimeException("getPassword() returned null");
        }
        if (pwd1.length != original.length) {
            throw new RuntimeException("getPassword() returned array of wrong length");
        }
        for (int i = 0; i < pwd1.length; i++) {
            if (pwd1[i] != original[i]) {
                throw new RuntimeException("getPassword() returned array with wrong content");
            }
        }
        // Check that returned array is a clone, not the internal array
        pwd1[0] = 'x';
        char[] pwd3 = spec.getPassword();
        if (pwd3[0] == 'x') {
            throw new RuntimeException("getPassword() returned internal array, not a clone");
        }
    }

    private static void testGetPasswordThrowsAfterClear() {
        char[] original = { 's', 'e', 'c', 'r', 'e', 't' };
        PBEKeySpec spec = new PBEKeySpec(original);

        // Clear the password by reflection is not allowed (no public method), so simulate by setting password to null via subclass
        // Since no public API to clear password, we create a subclass to nullify password field for test
        PBEKeySpec clearedSpec = new PBEKeySpec(original) {
            {
                try {
                    java.lang.reflect.Field f = PBEKeySpec.class.getDeclaredField("password");
                    f.setAccessible(true);
                    f.set(this, null);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        boolean threw = false;
        try {
            clearedSpec.getPassword();
        } catch (IllegalStateException e) {
            if (!"password has been cleared".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected exception message: " + e.getMessage());
            }
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException was not thrown when password is null");
        }
    }
}
