/*
 * @test
 * @summary Verify Field.getFloat results and exceptions for float fields, which are served by UnsafeFloatFieldAccessorImpl
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public float value = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeFloatFieldAccessorImplWitupTest holder = new UnsafeFloatFieldAccessorImplWitupTest();
        Field f = UnsafeFloatFieldAccessorImplWitupTest.class.getField("value");

        float v = f.getFloat(holder);
        if (v != 1.5f) {
            throw new RuntimeException("Expected 1.5f but got " + v);
        }

        try {
            f.getFloat("not a holder");
            throw new RuntimeException("Expected IllegalArgumentException for wrong object type");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getFloat(Integer.valueOf(1));
            throw new RuntimeException("Expected IllegalArgumentException for Integer object");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getFloat(null);
            throw new RuntimeException("Expected NullPointerException for null object");
        } catch (NullPointerException expected) {
        }
    }
}
