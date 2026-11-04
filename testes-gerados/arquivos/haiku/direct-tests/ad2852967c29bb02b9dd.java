/*
 * @test
 * @summary Verify that getChar on a static Object field is rejected with IllegalArgumentException
 * @run main UnsafeStaticObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeStaticObjectFieldAccessorImplWitupTest {
    static Object value = "data";

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticObjectFieldAccessorImplWitupTest.class.getDeclaredField("value");
        try {
            f.getChar(null);
            throw new RuntimeException("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
        if (!"data".equals(f.get(null))) {
            throw new RuntimeException("field value changed by rejected getChar");
        }
    }
}
