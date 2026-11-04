/* @test    @summary Test the ObjectStreamField(String, Class<?>) constructor for null name to verify NullPointerException is thrown    @run main ObjectStreamFieldWitupTest */

import java.io.ObjectStreamField;

public class ObjectStreamFieldWitupTest {

    public static void main(String[] args) {
        testNullNameThrowsException();
        testValidConstruction();
    }

    private static void testNullNameThrowsException() {
        boolean exceptionThrown = false;
        try {
            new ObjectStreamField(null, String.class);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when name is null");
        }
    }

    private static void testValidConstruction() {
        String name = "fieldName";
        Class<?> type = String.class;
        ObjectStreamField osf = new ObjectStreamField(name, type);
        if (!osf.name.equals(name)) {
            throw new AssertionError("Name mismatch");
        }
        if (osf.type != type) {
            throw new AssertionError("Type mismatch");
        }
        if (osf.unshared != false) {
            throw new AssertionError("Unshared should be false by default");
        }
    }
}
