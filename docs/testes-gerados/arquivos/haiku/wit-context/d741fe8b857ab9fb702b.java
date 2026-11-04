/*
 * @test
 * @summary Field.getShort on a static float field must throw IllegalArgumentException, exercising UnsafeStaticFloatFieldAccessorImpl.getShort through public reflection
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static float value = 1.5f;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getDeclaredField("value");
        checkShortRejected(f, null);
        checkShortRejected(f, new Object());
        float v = f.getFloat(null);
        if (v != 1.5f) {
            throw new RuntimeException("unexpected float value: " + v);
        }
    }

    static void checkShortRejected(Field f, Object obj) {
        try {
            f.getShort(obj);
            throw new RuntimeException("expected IllegalArgumentException from getShort");
        } catch (IllegalArgumentException expected) {
        }
    }
}
