/*
 * @test
 * @summary Verify Field.getInt widening of short fields, which dispatches to UnsafeShortFieldAccessorImpl.getInt
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    private short value;
    public short pub;

    public static void main(String[] args) throws Exception {
        UnsafeShortFieldAccessorImplWitupTest t = new UnsafeShortFieldAccessorImplWitupTest();

        Field f = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("value");
        f.setAccessible(true);

        f.setShort(t, (short) 12345);
        check(f.getInt(t) == 12345, "getInt positive value");

        f.setShort(t, (short) -2);
        check(f.getInt(t) == -2, "getInt negative value");

        f.setShort(t, Short.MIN_VALUE);
        check(f.getInt(t) == -32768, "getInt MIN_VALUE");

        f.setShort(t, Short.MAX_VALUE);
        check(f.getInt(t) == 32767, "getInt MAX_VALUE");

        f.setShort(t, (short) 0);
        check(f.getInt(t) == 0, "getInt zero");

        Field p = UnsafeShortFieldAccessorImplWitupTest.class.getDeclaredField("pub");
        p.setShort(t, (short) 7);
        check(p.getInt(t) == 7, "getInt on public field");
        check(((Short) p.get(t)).shortValue() == 7, "get returns boxed short");

        boolean threw = false;
        try {
            f.getInt(new Object());
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(threw, "getInt with wrong object type must throw IllegalArgumentException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
