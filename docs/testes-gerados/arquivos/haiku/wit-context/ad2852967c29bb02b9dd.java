/*
 * @test
 * @summary Verifies that Field.getChar on a static Object field reaches UnsafeStaticObjectFieldAccessorImpl.getChar and throws IllegalArgumentException
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {

    public static Object objField = "value";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getField("objField");
        try {
            f.getChar(null);
            throw new RuntimeException("Expected IllegalArgumentException from getChar on Object field");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getChar(new UnsafeStaticObjectFieldAccessorImplWitupTest());
            throw new RuntimeException("Expected IllegalArgumentException from getChar with non-null receiver");
        } catch (IllegalArgumentException expected) {
        }
    }
}
