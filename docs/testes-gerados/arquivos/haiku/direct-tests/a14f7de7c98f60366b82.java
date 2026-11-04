/*
 * @test
 * @summary Field.getLong on a static float field must throw IllegalArgumentException, while getFloat still returns the value
 * @run main UnsafeStaticFloatFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticFloatFieldAccessorImplWitupTest {
    public static float value = 1.5f;
    public static final float FINAL_VALUE = 2.5f;

    public static void main(String[] args) throws Exception {
        checkGetLongThrows("value");
        checkGetLongThrows("FINAL_VALUE");

        Field f = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField("value");
        if (f.getFloat(null) != 1.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }

        Field ff = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField("FINAL_VALUE");
        if (ff.getFloat(null) != 2.5f) {
            throw new RuntimeException("getFloat on final field returned unexpected value");
        }
    }

    static void checkGetLongThrows(String name) throws Exception {
        Field f = UnsafeStaticFloatFieldAccessorImplWitupTest.class.getField(name);
        try {
            long r = f.getLong(null);
            throw new RuntimeException("no IllegalArgumentException for " + name + ", returned " + r);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
