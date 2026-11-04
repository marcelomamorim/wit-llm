/*
 * @test
 * @summary Verifies that Field.getInt on a reference (Object) field throws IllegalArgumentException, exercising UnsafeObjectFieldAccessorImpl.getInt through the public reflection API
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    public static class Holder {
        public Object ref = "value";
        public Object nullRef = null;
        public Object boxed = Integer.valueOf(42);
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field ref = Holder.class.getField("ref");
        expectIAE(ref, h);

        Field nullRef = Holder.class.getField("nullRef");
        expectIAE(nullRef, h);

        Field boxed = Holder.class.getField("boxed");
        expectIAE(boxed, h);
    }

    private static void expectIAE(Field f, Object target) throws Exception {
        try {
            f.getInt(target);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException for field " + f.getName());
    }
}
