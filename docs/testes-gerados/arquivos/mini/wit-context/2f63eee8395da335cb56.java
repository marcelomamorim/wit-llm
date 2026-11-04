/*
 * @test
 * @summary Test java.util.jar.Attributes.Name constructor for null and invalid length inputs
 * @run main AttributesWitupTest
 */

public class AttributesWitupTest {
    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testEmptyNameThrowsIAE();
        testTooLongNameThrowsIAE();
        testValidNameSucceeds();
    }

    private static void testNullNameThrowsNPE() {
        try {
            new java.util.jar.Attributes.Name(null);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyNameThrowsIAE() {
        try {
            new java.util.jar.Attributes.Name("");
            throw new RuntimeException("Expected IllegalArgumentException for empty name");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testTooLongNameThrowsIAE() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 71; i++) {
            sb.append('a');
        }
        String longName = sb.toString();
        try {
            new java.util.jar.Attributes.Name(longName);
            throw new RuntimeException("Expected IllegalArgumentException for name length > 70");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidNameSucceeds() {
        String validName = "Valid-Name_123";
        java.util.jar.Attributes.Name name = new java.util.jar.Attributes.Name(validName);
        if (!validName.equals(name.toString())) {
            throw new RuntimeException("Name.toString() does not match input string");
        }
    }
}
