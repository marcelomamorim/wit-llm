/*
 * @test
 * @summary Test ObjectStreamField constructor with name, type and unshared flag
 * @run main ObjectStreamFieldWitupTest
 */

public class ObjectStreamFieldWitupTest {
    public static void main(String[] args) {
        testValidConstruction();
        testNullNameThrows();
    }

    private static void testValidConstruction() {
        java.io.ObjectStreamField osf1 = new java.io.ObjectStreamField("fieldName", String.class, false);
        if (!"fieldName".equals(osf1.getName())) {
            throw new RuntimeException("Name mismatch");
        }
        if (osf1.getType() != String.class) {
            throw new RuntimeException("Type mismatch");
        }
        if (osf1.isUnshared()) {
            throw new RuntimeException("Unshared flag mismatch: expected false");
        }

        java.io.ObjectStreamField osf2 = new java.io.ObjectStreamField("fieldName", int.class, true);
        if (!"fieldName".equals(osf2.getName())) {
            throw new RuntimeException("Name mismatch");
        }
        if (osf2.getType() != int.class) {
            throw new RuntimeException("Type mismatch");
        }
        if (!osf2.isUnshared()) {
            throw new RuntimeException("Unshared flag mismatch: expected true");
        }
    }

    private static void testNullNameThrows() {
        try {
            new java.io.ObjectStreamField(null, Object.class, false);
            throw new RuntimeException("Expected NullPointerException for null name");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
