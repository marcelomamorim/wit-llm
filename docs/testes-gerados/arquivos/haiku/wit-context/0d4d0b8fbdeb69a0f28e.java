/*
 * @test
 * @summary Verify that Field.getChar on a volatile Object field (dispatched to UnsafeQualifiedObjectFieldAccessorImpl) throws IllegalArgumentException
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public volatile Object value = "x";

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest holder = new UnsafeQualifiedObjectFieldAccessorImplWitupTest();
        Field field = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getField("value");

        Object read = field.get(holder);
        if (!"x".equals(read)) {
            throw new RuntimeException("Unexpected value from get: " + read);
        }

        boolean thrown = false;
        try {
            field.getChar(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getChar did not throw IllegalArgumentException");
        }
    }
}
