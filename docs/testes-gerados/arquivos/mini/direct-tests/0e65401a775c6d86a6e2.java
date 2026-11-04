/*
 * @test
 * @summary Test NamedParameterSpec constructor and getName method
 * @run main NamedParameterSpecWitupTest
 */

import java.security.spec.NamedParameterSpec;

public class NamedParameterSpecWitupTest {
    public static void main(String[] args) {
        testValidName();
        testNullNameThrows();
    }

    private static void testValidName() {
        String name = "TestName";
        NamedParameterSpec spec = new NamedParameterSpec(name);
        if (!name.equals(spec.getName())) {
            throw new AssertionError("Expected name '" + name + "' but got '" + spec.getName() + "'");
        }
    }

    private static void testNullNameThrows() {
        try {
            new NamedParameterSpec(null);
            throw new AssertionError("Expected NullPointerException for null stdName");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("stdName must not be null")) {
                throw new AssertionError("NullPointerException message unexpected: " + msg);
            }
        }
    }
}
