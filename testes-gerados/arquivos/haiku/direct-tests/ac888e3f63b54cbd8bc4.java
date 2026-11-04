/*
 * @test
 * @summary Verifies that Field.getShort on a static volatile Object field (served by UnsafeQualifiedStaticObjectFieldAccessorImpl) throws IllegalArgumentException
 * @run main UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest {

    static volatile Object value = "x";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("value");

        Object current = f.get(null);
        if (!"x".equals(current)) {
            throw new RuntimeException("Unexpected get() result: " + current);
        }

        checkGetShortThrows(f, null);
        checkGetShortThrows(f, new Object());
    }

    private static void checkGetShortThrows(Field f, Object obj) throws Exception {
        boolean thrown = false;
        try {
            f.getShort(obj);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("getShort did not throw IllegalArgumentException");
        }
    }
}
