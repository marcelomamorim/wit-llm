/*
 * @test
 * @summary Verify that Field.getChar on a static double field throws IllegalArgumentException through the public reflection API
 * @run main UnsafeStaticDoubleFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticDoubleFieldAccessorImplWitupTest {
    public static double value = 1.5d;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticDoubleFieldAccessorImplWitupTest.class.getDeclaredField("value");

        checkGetCharThrows(f, null);
        checkGetCharThrows(f, new Object());

        if (f.getDouble(null) != 1.5d) {
            throw new AssertionError("getDouble must still return the field value");
        }
    }

    private static void checkGetCharThrows(Field f, Object obj) {
        try {
            f.getChar(obj);
        } catch (IllegalArgumentException expected) {
            return;
        } catch (IllegalAccessException e) {
            throw new AssertionError("unexpected IllegalAccessException", e);
        }
        throw new AssertionError("expected IllegalArgumentException from getChar");
    }
}
