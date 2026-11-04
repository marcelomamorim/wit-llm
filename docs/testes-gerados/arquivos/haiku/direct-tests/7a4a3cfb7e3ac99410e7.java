/*
 * @test
 * @summary Field.getChar on a static short field must throw IllegalArgumentException
 * @run main UnsafeQualifiedStaticShortFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeQualifiedStaticShortFieldAccessorImplWitupTest {
    public static short s = 7;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeQualifiedStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("s");

        try {
            f.getChar(null);
            throw new AssertionError("expected IllegalArgumentException for getChar(null)");
        } catch (IllegalArgumentException expected) {
        }

        try {
            f.getChar(new Object());
            throw new AssertionError("expected IllegalArgumentException for getChar(obj)");
        } catch (IllegalArgumentException expected) {
        }

        if (f.getShort(null) != 7) {
            throw new AssertionError("getShort should still return the field value");
        }
    }
}
