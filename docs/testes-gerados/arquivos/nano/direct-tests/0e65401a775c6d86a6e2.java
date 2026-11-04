/* @test    @summary Test constructor of NamedParameterSpec with valid and null input    @run main NamedParameterSpecWitupTest */

import java.security.spec.NamedParameterSpec;

public class NamedParameterSpecWitupTest {

    public static void main(String[] args) {
        testValidName();
        testNullName();
    }

    static void testValidName() {
        String validName = "X25519";
        NamedParameterSpec spec = new NamedParameterSpec(validName);
        assert spec.getName().equals(validName) : "Expected name to be " + validName;
    }

    static void testNullName() {
        boolean threwException = false;
        try {
            new NamedParameterSpec(null);
        } catch (NullPointerException e) {
            threwException = true;
        }
        assert threwException : "Expected NullPointerException when passing null";
    }
}
