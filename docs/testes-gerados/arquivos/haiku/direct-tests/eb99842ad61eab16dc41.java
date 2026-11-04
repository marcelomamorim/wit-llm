/*
 * @test
 * @summary Field.getBoolean on a reference-typed field must throw IllegalArgumentException via UnsafeObjectFieldAccessorImpl
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {
    public Object obj = "value";

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest holder = new UnsafeObjectFieldAccessorImplWitupTest();
        Field f = UnsafeObjectFieldAccessorImplWitupTest.class.getField("obj");

        boolean threw = false;
        try {
            f.getBoolean(holder);
        } catch (IllegalArgumentException expected) {
            threw = true;
        }
        if (!threw) {
            throw new RuntimeException("expected IllegalArgumentException from getBoolean on Object field");
        }

        Object value = f.get(holder);
        if (!"value".equals(value)) {
            throw new RuntimeException("unexpected value after failed getBoolean: " + value);
        }
    }
}
