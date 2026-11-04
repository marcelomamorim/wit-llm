/*
 * @test
 * @summary Verifies that Field.getFloat on a reference field is rejected with IllegalArgumentException, exercising the UnsafeObjectFieldAccessorImpl.getFloat path
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {
    Object ref = "value";

    public static void main(String[] args) throws Exception {
        UnsafeObjectFieldAccessorImplWitupTest holder = new UnsafeObjectFieldAccessorImplWitupTest();
        Field field = UnsafeObjectFieldAccessorImplWitupTest.class.getDeclaredField("ref");

        Object value = field.get(holder);
        if (!"value".equals(value)) {
            throw new RuntimeException("Unexpected value from get: " + value);
        }

        boolean thrown = false;
        try {
            field.getFloat(holder);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getFloat on Object field");
        }
    }
}
