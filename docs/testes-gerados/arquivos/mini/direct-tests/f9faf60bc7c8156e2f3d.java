/*
 * @test
 * @summary Test javax.crypto.spec.PBEKeySpec.getPassword() behavior
 * @run main PBEKeySpecWitupTest
 */

import javax.crypto.spec.PBEKeySpec;

public class PBEKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testGetPasswordReturnsClone();
        testGetPasswordThrowsAfterClear();
    }

    private static void testGetPasswordReturnsClone() {
        char[] original = { 'a', 'b', 'c' };
        PBEKeySpec spec = new PBEKeySpec(original);
        char[] pwd1 = spec.getPassword();
        char[] pwd2 = spec.getPassword();

        if (pwd1 == pwd2) {
            throw new RuntimeException("getPassword() must return a new clone, not the same array");
        }

        if (pwd1.length != original.length) {
            throw new RuntimeException("Returned password length mismatch");
        }

        for (int i = 0; i < pwd1.length; i++) {
            if (pwd1[i] != original[i]) {
                throw new RuntimeException("Returned password content mismatch at index " + i);
            }
        }

        // Modify returned array and verify original inside spec is not affected
        pwd1[0] = 'z';
        char[] pwd3 = spec.getPassword();
        if (pwd3[0] != original[0]) {
            throw new RuntimeException("Internal password was modified by external change");
        }
    }

    private static void testGetPasswordThrowsAfterClear() {
        char[] pwd = { 'x', 'y', 'z' };
        PBEKeySpec spec = new PBEKeySpec(pwd);

        // Clear the password by reflection since no public clear method exists
        // Reflection is justified here because the field is private and no public API to clear
        try {
            java.lang.reflect.Field f = PBEKeySpec.class.getDeclaredField("password");
            f.setAccessible(true);
            f.set(spec, null);
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed", e);
        }

        boolean threw = false;
        try {
            spec.getPassword();
        } catch (IllegalStateException ise) {
            if ("password has been cleared".equals(ise.getMessage())) {
                threw = true;
            } else {
                throw new RuntimeException("IllegalStateException thrown with unexpected message", ise);
            }
        }

        if (!threw) {
            throw new RuntimeException("Expected IllegalStateException was not thrown after password cleared");
        }
    }
}
