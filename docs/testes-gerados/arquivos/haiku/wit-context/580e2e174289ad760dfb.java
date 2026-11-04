/*
 * @test
 * @summary Verifies that reading a float field as int through the public reflection API throws IllegalArgumentException, while getFloat still returns the stored value
 * @run main UnsafeQualifiedFloatFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedFloatFieldAccessorImplWitupTest {

    public float value = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedFloatFieldAccessorImplWitupTest t = new UnsafeQualifiedFloatFieldAccessorImplWitupTest();
        Field f = UnsafeQualifiedFloatFieldAccessorImplWitupTest.class.getField("value");

        boolean thrown = false;
        try {
            f.getInt(t);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected IllegalArgumentException from getInt on float field");
        }

        if (f.getFloat(t) != 1.5f) {
            throw new AssertionError("getFloat returned unexpected value");
        }
    }
}
