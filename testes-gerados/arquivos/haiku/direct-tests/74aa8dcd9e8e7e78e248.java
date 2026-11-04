/*
 * @test
 * @summary Field.getInt on a float instance field must throw IllegalArgumentException (reached via UnsafeFloatFieldAccessorImpl.getInt)
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {
    public static class Holder {
        public float f = 1.5f;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field f = Holder.class.getDeclaredField("f");

        expectIllegalArgument(f, h);

        if (f.getFloat(h) != 1.5f) {
            throw new RuntimeException("getFloat returned unexpected value");
        }
    }

    static void expectIllegalArgument(Field f, Object o) {
        try {
            f.getInt(o);
        } catch (IllegalArgumentException e) {
            return;
        } catch (IllegalAccessException e) {
            throw new RuntimeException("unexpected IllegalAccessException", e);
        }
        throw new RuntimeException("expected IllegalArgumentException from getInt on float field");
    }
}
