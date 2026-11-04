/* @test    @summary Test ObjectStreamField constructor with name and type parameters    @run main ObjectStreamFieldWitupTest */

import java.io.ObjectStreamField;
import java.lang.reflect.Field;

public class ObjectStreamFieldWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidInputs();
        testConstructorWithNullName();
        testConstructorWithPrimitiveType();
    }

    static void testConstructorWithValidInputs() {
        String fieldName = "testField";
        Class<?> fieldType = String.class;
        ObjectStreamField osf = new ObjectStreamField(fieldName, fieldType);
        assert osf != null : "ObjectStreamField instance should not be null";
        assert osf.name.equals(fieldName) : "Field name mismatch";
        assert osf.type == fieldType : "Field type mismatch";
        assert osf.unshared == false : "Default unshared should be false";
    }

    static void testConstructorWithNullName() {
        try {
            new ObjectStreamField(null, String.class);
            assert false : "Expected NullPointerException for null name";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testConstructorWithPrimitiveType() {
        ObjectStreamField osf = new ObjectStreamField("primitiveField", int.class);
        assert osf != null : "ObjectStreamField instance should not be null";
        assert osf.name.equals("primitiveField") : "Field name mismatch";
        assert osf.type == int.class : "Field type should be int.class";
        assert osf.unshared == false : "Default unshared should be false";
    }
}
