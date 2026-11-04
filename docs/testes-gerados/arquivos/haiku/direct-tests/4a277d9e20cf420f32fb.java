/*
 * @test
 * @summary Verify that Field.getBoolean on a long field throws IllegalArgumentException, which is the observable behavior of UnsafeLongFieldAccessorImpl.getBoolean
 * @run main UnsafeLongFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeLongFieldAccessorImplWitupTest {

    public static class Holder {
        public long value = 42L;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("value");
        Holder h = new Holder();

        boolean thrown = false;
        try {
            f.getBoolean(h);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getBoolean on long field");
        }

        Object v = f.get(h);
        if (!(v instanceof Long) || ((Long) v).longValue() != 42L) {
            throw new RuntimeException("Unexpected value from get: " + v);
        }
    }
}
