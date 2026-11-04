/*
 * @test
 * @summary Verify that Field.getByte on a static short field is rejected with IllegalArgumentException while getShort still returns the value
 * @run main UnsafeStaticShortFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeStaticShortFieldAccessorImplWitupTest {
    public static short S = 42;

    public static void main(String[] args) throws Exception {
        Field f = UnsafeStaticShortFieldAccessorImplWitupTest.class.getDeclaredField("S");

        if (f.getShort(null) != 42) {
            throw new AssertionError("getShort(null) should return 42");
        }

        boolean thrownNull = false;
        try {
            f.getByte(null);
        } catch (IllegalArgumentException e) {
            thrownNull = true;
        }
        if (!thrownNull) {
            throw new AssertionError("getByte(null) should throw IllegalArgumentException");
        }

        boolean thrownObj = false;
        try {
            f.getByte(new Object());
        } catch (IllegalArgumentException e) {
            thrownObj = true;
        }
        if (!thrownObj) {
            throw new AssertionError("getByte(obj) should throw IllegalArgumentException");
        }

        if (f.getShort(null) != 42) {
            throw new AssertionError("value must be unchanged after rejected getByte");
        }
    }
}
