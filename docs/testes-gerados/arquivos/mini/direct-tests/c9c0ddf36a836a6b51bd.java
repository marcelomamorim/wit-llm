/*
 * @test
 * @summary Test ObjectStreamField(String, Class<?>) constructor
 * @run main ObjectStreamFieldWitupTest
 */

public class ObjectStreamFieldWitupTest {
    public static void main(String[] args) {
        // Test with valid name and type
        java.io.ObjectStreamField osf1 = new java.io.ObjectStreamField("fieldName", String.class);
        if (!"fieldName".equals(osf1.getName())) {
            throw new RuntimeException("Name mismatch");
        }
        if (osf1.getType() != String.class) {
            throw new RuntimeException("Type mismatch");
        }
        if (osf1.isUnshared()) {
            throw new RuntimeException("Unshared should be false by default");
        }

        // Test with primitive type
        java.io.ObjectStreamField osf2 = new java.io.ObjectStreamField("intField", int.class);
        if (!"intField".equals(osf2.getName())) {
            throw new RuntimeException("Name mismatch for primitive");
        }
        if (osf2.getType() != int.class) {
            throw new RuntimeException("Type mismatch for primitive");
        }

        // Test with null name - should throw NullPointerException
        try {
            new java.io.ObjectStreamField(null, Object.class);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException npe) {
            // expected
        }

        // Test with null type - allowed, type can be null
        java.io.ObjectStreamField osf3 = new java.io.ObjectStreamField("nullType", null);
        if (!"nullType".equals(osf3.getName())) {
            throw new RuntimeException("Name mismatch for null type");
        }
        if (osf3.getType() != null) {
            throw new RuntimeException("Type should be null");
        }
    }
}
