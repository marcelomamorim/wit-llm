/*
 * @test
 * @summary Field.getByte on a final Object field must throw IllegalArgumentException via UnsafeQualifiedObjectFieldAccessorImpl.getByte
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    final Object ref = "value";

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedObjectFieldAccessorImplWitupTest holder = new UnsafeQualifiedObjectFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedObjectFieldAccessorImplWitupTest.class.getDeclaredField("ref");

        Object read = f.get(holder);
        if (!"value".equals(read)) {
            throw new RuntimeException("unexpected get result: " + read);
        }

        boolean thrown = false;
        try {
            byte b = f.getByte(holder);
            throw new RuntimeException("getByte did not throw, returned: " + b);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException expected");
        }
    }
}
