/*
 * @test
 * @summary Verifies that Field.getFloat on a short field returns the widened value and rejects an object of an unrelated type
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    public short value;

    public static void main(String[] args) throws Exception {
        UnsafeShortFieldAccessorImplWitupTest t = new UnsafeShortFieldAccessorImplWitupTest();
        Field f = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("value");

        t.value = -5;
        check(f.getFloat(t) == -5.0f, "negative short widened to float");

        t.value = 0;
        check(f.getFloat(t) == 0.0f, "zero short widened to float");

        t.value = Short.MAX_VALUE;
        check(f.getFloat(t) == 32767.0f, "max short widened to float");

        boolean thrown = false;
        try {
            f.getFloat(new Object());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "IllegalArgumentException for object of unrelated type");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
