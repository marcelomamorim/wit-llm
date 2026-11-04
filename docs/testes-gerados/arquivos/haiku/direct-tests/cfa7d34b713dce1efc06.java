/*
 * @test
 * @summary Verify that Field.getChar on a long field throws IllegalArgumentException via the UnsafeLongFieldAccessorImpl path, while Field.get still returns the long value.
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
            f.getChar(h);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException from getChar on long field");
        }

        Object boxed = f.get(h);
        if (!(boxed instanceof Long) || ((Long) boxed).longValue() != 42L) {
            throw new RuntimeException("Field.get did not return expected Long 42: " + boxed);
        }
    }
}
