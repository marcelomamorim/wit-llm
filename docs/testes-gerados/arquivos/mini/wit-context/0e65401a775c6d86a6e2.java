/*
 * @test
 * @summary Test NamedParameterSpec constructor behavior including null argument check
 * @run main NamedParameterSpecWitupTest
 */

import java.security.spec.NamedParameterSpec;

public class NamedParameterSpecWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidName();
        testConstructorWithNullName();
    }

    private static void testConstructorWithValidName() {
        String validName = "X25519";
        NamedParameterSpec spec = new NamedParameterSpec(validName);
        if (!validName.equals(spec.getName())) {
            throw new AssertionError("Expected name to be '" + validName + "' but was '" + spec.getName() + "'");
        }
    }

    private static void testConstructorWithNullName() {
        try {
            new NamedParameterSpec(null);
            throw new AssertionError("Expected NullPointerException for null stdName argument");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("stdName must not be null")) {
                throw new AssertionError("NullPointerException message unexpected: " + msg);
            }
        }
    }
}
