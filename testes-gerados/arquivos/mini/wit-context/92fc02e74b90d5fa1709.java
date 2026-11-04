/*
 * @test
 * @summary Test ObjectStreamField constructor with name, type and unshared flag
 * @run main ObjectStreamFieldWitupTest
 */

public class ObjectStreamFieldWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidArgs();
        testConstructorWithNullName();
    }

    private static void testConstructorWithValidArgs() {
        java.io.ObjectStreamField field1 = new java.io.ObjectStreamField("fieldName", String.class, false);
        if (!"fieldName".equals(field1.getName())) {
            throw new AssertionError("Name mismatch");
        }
        if (field1.getType() != String.class) {
            throw new AssertionError("Type mismatch");
        }
        if (field1.isUnshared()) {
            throw new AssertionError("Unshared flag mismatch");
        }

        java.io.ObjectStreamField field2 = new java.io.ObjectStreamField("fieldName", Integer.class, true);
        if (!"fieldName".equals(field2.getName())) {
            throw new AssertionError("Name mismatch");
        }
        if (field2.getType() != Integer.class) {
            throw new AssertionError("Type mismatch");
        }
        if (!field2.isUnshared()) {
            throw new AssertionError("Unshared flag mismatch");
        }
    }

    private static void testConstructorWithNullName() {
        try {
            new java.io.ObjectStreamField(null, String.class, false);
            throw new AssertionError("Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
