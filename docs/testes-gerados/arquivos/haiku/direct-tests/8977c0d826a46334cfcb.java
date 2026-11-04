/*
 * @test
 * @summary Field.getByte on a static Object field must throw IllegalArgumentException through the public reflection API
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {
    public static final Object VALUE = "x";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getField("VALUE");
        try {
            f.getByte(null);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            // expected: getByte on an Object-typed field is not allowed
        }
    }
}
