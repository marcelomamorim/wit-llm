/*
 * @test
 * @summary Field.getLong on a double field must reach UnsafeDoubleFieldAccessorImpl.getLong and throw IllegalArgumentException
 * @run main UnsafeDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeDoubleFieldAccessorImplWitupTest {

    public static class Holder {
        public double d = 1.5;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getField("d");
        Holder h = new Holder();

        boolean thrown = false;
        try {
            long v = f.getLong(h);
            throw new RuntimeException("expected IllegalArgumentException, got value " + v);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException not thrown");
        }

        thrown = false;
        try {
            f.getLong(null);
            throw new RuntimeException("expected IllegalArgumentException for null object");
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("IllegalArgumentException not thrown for null object");
        }
    }
}
