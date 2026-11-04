/*
 * @test
 * @summary Field.getShort on a float field must throw IllegalArgumentException, as UnsafeFloatFieldAccessorImpl.getShort does
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public float f = 1.5f;

    public static void main(String[] args) throws Exception {
        UnsafeFloatFieldAccessorImplWitupTest t = new UnsafeFloatFieldAccessorImplWitupTest();
        Field field = UnsafeFloatFieldAccessorImplWitupTest.class.getField("f");

        boolean thrown = false;
        try {
            field.getShort(t);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getShort on float field");
        }

        if (field.getFloat(t) != 1.5f) {
            throw new RuntimeException("getFloat should still return the stored value");
        }
    }
}
