/*
 * @test
 * @summary Field.getInt on a short field reaches UnsafeShortFieldAccessorImpl.getInt and rejects objects of an unrelated type
 * @run main UnsafeShortFieldAccessorImplWitupTest
 */
import java.lang.reflect.Field;

public class UnsafeShortFieldAccessorImplWitupTest {
    static class Holder {
        private short v;

        Holder(short v) {
            this.v = v;
        }
    }

    static class Other {
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("v");
        f.setAccessible(true);

        check(f.getInt(new Holder((short) -5)) == -5, "negative short value not widened to int");
        check(f.getInt(new Holder(Short.MIN_VALUE)) == Short.MIN_VALUE, "MIN_VALUE not returned");
        check(f.getInt(new Holder(Short.MAX_VALUE)) == Short.MAX_VALUE, "MAX_VALUE not returned");
        check(f.getInt(new Holder((short) 0)) == 0, "zero value not returned");

        boolean threw = false;
        try {
            f.getInt(new Other());
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        check(threw, "expected IllegalArgumentException for object of unrelated type");
    }
}
