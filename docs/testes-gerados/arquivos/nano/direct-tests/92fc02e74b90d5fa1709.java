/* @test    @summary Test ObjectStreamField constructor with valid parameters    @run main ObjectStreamFieldWitupTest */

import java.io.ObjectStreamField;

public class ObjectStreamFieldWitupTest {

    public static void main(String[] args) {
        testValidConstructor();
        testNullNameThrows();
    }

    static void testValidConstructor() {
        String name = "testField";
        Class<?> type = String.class;
        boolean unshared = true;
        ObjectStreamField field = new ObjectStreamField(name, type, unshared);
        assert field != null : "ObjectStreamField instance should not be null";
    }

    static void testNullNameThrows() {
        try {
            new ObjectStreamField(null, String.class, false);
            assert false : "Expected NullPointerException for null name";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
