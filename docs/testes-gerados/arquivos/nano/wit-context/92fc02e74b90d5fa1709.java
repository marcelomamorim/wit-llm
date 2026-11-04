/*
 * @test
 * @summary Test for ObjectStreamField constructor with null name
 * @run main ObjectStreamFieldWitupTest
 */

import java.io.ObjectStreamField;

public class ObjectStreamFieldWitupTest {

    public static void main(String[] args) {
        testConstructorWithNullName();
        testConstructorWithValidParameters();
    }

    static void testConstructorWithNullName() {
        try {
            new ObjectStreamField(null, String.class, false);
            System.err.println("Failed: Expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            System.out.println("Passed: NullPointerException thrown as expected for null name");
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    static void testConstructorWithValidParameters() {
        String name = "fieldName";
        Class<?> type = String.class;
        boolean unshared = true;
        ObjectStreamField osf = new ObjectStreamField(name, type, unshared);
        if (osf != null && osf.name.equals(name) && osf.type == type && osf.unshared == unshared) {
            System.out.println("Passed: Constructor with valid parameters");
        } else {
            System.err.println("Failed: Constructor with valid parameters did not initialize fields correctly");
        }
    }
}
