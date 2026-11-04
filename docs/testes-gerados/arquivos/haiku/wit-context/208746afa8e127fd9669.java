/*
 * @test
 * @summary Field.getShort on a float instance field must throw IllegalArgumentException (UnsafeFloatFieldAccessorImpl.getShort)
 * @run main UnsafeFloatFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeFloatFieldAccessorImplWitupTest {

    static class Holder {
        float f = 1.5f;
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();
        Field field = Holder.class.getDeclaredField("f");
        expectIllegalArgument(field, h);
        expectIllegalArgument(field, new Holder());
    }

    static void expectIllegalArgument(Field field, Object obj) throws Exception {
        try {
            short s = field.getShort(obj);
            throw new RuntimeException("Expected IllegalArgumentException, got short " + s);
        } catch (IllegalArgumentException expected) {
        }
    }
}
