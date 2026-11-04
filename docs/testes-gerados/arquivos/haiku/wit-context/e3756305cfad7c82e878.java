/*
 * @test
 * @summary Verifies that Field.getLong on a volatile boolean field (served by UnsafeQualifiedBooleanFieldAccessorImpl) throws IllegalArgumentException
 * @run main UnsafeQualifiedBooleanFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedBooleanFieldAccessorImplWitupTest {

    static class Holder {
        volatile boolean flag = true;
    }

    public static void main(String[] args) throws Exception {
        Holder holder = new Holder();
        Field field = Holder.class.getDeclaredField("flag");

        boolean threw = false;
        try {
            field.getLong(holder);
        } catch (IllegalArgumentException expected) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("Expected IllegalArgumentException from getLong on boolean field");
        }

        if (!field.getBoolean(holder)) {
            throw new AssertionError("Boolean value changed unexpectedly");
        }
    }
}
