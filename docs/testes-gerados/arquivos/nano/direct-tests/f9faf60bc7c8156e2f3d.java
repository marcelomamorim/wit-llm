/* @test    @summary Test getPassword() method of PBEKeySpec for normal and cleared state    @run main PBEKeySpecWitupTest */

import javax.crypto.spec.PBEKeySpec;
import java.lang.reflect.Method;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testGetPasswordNormal();
        testGetPasswordAfterClear();
    }

    private static void testGetPasswordNormal() {
        char[] password = {'a', 'b', 'c'};
        PBEKeySpec spec = new PBEKeySpec(password);
        char[] result = spec.getPassword();
        assert result != null : "getPassword() returned null";
        assert result.length == password.length : "Password length mismatch";
        for (int i = 0; i < password.length; i++) {
            assert result[i] == password[i] : "Password content mismatch at index " + i;
        }
    }

    private static void testGetPasswordAfterClear() throws Exception {
        char[] password = {'x', 'y', 'z'};
        PBEKeySpec spec = new PBEKeySpec(password);
        // Use reflection to set password to null to simulate cleared state
        Method getPasswordMethod = PBEKeySpec.class.getDeclaredMethod("getPassword");
        getPasswordMethod.setAccessible(true);
        // Access the private field 'password'
        java.lang.reflect.Field passwordField = PBEKeySpec.class.getDeclaredField("password");
        passwordField.setAccessible(true);
        passwordField.set(spec, null); // simulate cleared password
        boolean exceptionThrown = false;
        try {
            spec.getPassword();
        } catch (IllegalStateException e) {
            exceptionThrown = true;
            assert e.getMessage().equals("password has been cleared") : "Unexpected exception message";
        }
        assert exceptionThrown : "Expected IllegalStateException was not thrown";
    }
}
