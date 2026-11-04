/*
 * @test
 * @summary Test ObjectStreamField constructors for null name and normal usage
 * @run main ObjectStreamFieldWitupTest
 */

public class ObjectStreamFieldWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidName();
        testConstructorWithNullName();
    }

    private static void testConstructorWithValidName() {
        java.io.ObjectStreamField osf = new java.io.ObjectStreamField("fieldName", String.class);
        if (!"fieldName".equals(osf.getName())) {
            throw new AssertionError("Expected name 'fieldName', got: " + osf.getName());
        }
        if (osf.getType() != String.class) {
            throw new AssertionError("Expected type String.class, got: " + osf.getType());
        }
        if (osf.isUnshared()) {
            throw new AssertionError("Expected unshared to be false");
        }
    }

    private static void testConstructorWithNullName() {
        try {
            new java.io.ObjectStreamField(null, String.class);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
