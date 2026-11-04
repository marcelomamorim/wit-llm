/*
 * @test
 * @summary Verifies that Field.getLong on a static float field throws IllegalArgumentException, which is the public observable behavior of UnsafeQualifiedStaticFloatFieldAccessorImpl.getLong
 * @run main UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest {

    static final float FINAL_FLOAT = 1.5f;
    static float mutableFloat = 2.5f;

    public static void main(String[] args) throws Exception {
        checkFinalFloatGetLongThrows();
        checkMutableFloatGetLongThrows();
        checkFinalFloatGetStillReturnsFloat();
    }

    static void checkFinalFloatGetLongThrows() throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_FLOAT");
        try {
            f.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException for getLong on final static float");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void checkMutableFloatGetLongThrows() throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("mutableFloat");
        try {
            f.getLong(null);
            throw new RuntimeException("Expected IllegalArgumentException for getLong on static float");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void checkFinalFloatGetStillReturnsFloat() throws Exception {
        Field f = UnsafeQualifiedStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("FINAL_FLOAT");
        Object value = f.get(null);
        if (!(value instanceof Float) || ((Float) value).floatValue() != 1.5f) {
            throw new RuntimeException("Unexpected value from get: " + value);
        }
    }
}
