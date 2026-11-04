/*
 * @test
 * @summary Verifies that primitive char reads on a static volatile Object field are rejected through the public Field API
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    static volatile Object objectField = "value";

    public static void main(String[] args) throws Exception {
        Field field = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("objectField");
        field.setAccessible(true);

        boolean thrown = false;
        try {
            field.getChar(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on an Object field");
        }

        Object value = field.get(null);
        if (!"value".equals(value)) {
            throw new RuntimeException("Object read returned unexpected value: " + value);
        }
    }
}
