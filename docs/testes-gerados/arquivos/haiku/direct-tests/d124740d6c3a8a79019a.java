/*
 * @test
 * @summary Verify that reading a float field as boolean through reflection is rejected by UnsafeFloatFieldAccessorImpl
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public float value = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeFloatFieldAccessorImplWitupTest t = new UnsafeFloatFieldAccessorImplWitupTest();
        Field f = UnsafeFloatFieldAccessorImplWitupTest.class.getField("value");

        if (f.getFloat(t) != 1.5f) {
            throw new AssertionError("getFloat should return 1.5f");
        }

        boolean thrown = false;
        try {
            f.getBoolean(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("getBoolean on a float field must throw IllegalArgumentException");
        }
    }
}
