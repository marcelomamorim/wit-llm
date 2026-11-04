/*
 * @test
 * @summary Verify that Field.getByte on a double field is rejected, which routes to UnsafeQualifiedDoubleFieldAccessorImpl.getByte
 * @run main UnsafeQualifiedDoubleFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedDoubleFieldAccessorImplWitupTest {

    public static class Target {
        public double plain = 1.5;
        public volatile double vol = 2.5;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectIAE(Field f, Object obj) throws Exception {
        try {
            byte b = f.getByte(obj);
            throw new RuntimeException("Expected IllegalArgumentException, got byte " + b
                    + " for field " + f.getName());
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    public static void main(String[] args) throws Exception {
        Target t = new Target();

        Field plain = Target.class.getField("plain");
        expectIAE(plain, t);
        check(plain.getDouble(t) == 1.5, "plain value changed after rejected getByte");

        Field vol = Target.class.getField("vol");
        expectIAE(vol, t);
        check(vol.getDouble(t) == 2.5, "volatile value changed after rejected getByte");
    }
}
