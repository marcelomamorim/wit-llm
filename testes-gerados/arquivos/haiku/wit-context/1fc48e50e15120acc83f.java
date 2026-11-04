/*
 * @test
 * @summary Verify that Field.getShort on a reference-typed field throws IllegalArgumentException, as the object accessor contract requires
 * @run main UnsafeQualifiedObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedObjectFieldAccessorImplWitupTest {

    public static class Holder {
        public Object ref = "x";
        public final Object fin = "y";
    }

    public static void main(String[] args) throws Exception {
        Holder h = new Holder();

        Field ref = Holder.class.getField("ref");
        try {
            ref.getShort(h);
            throw new AssertionError("Expected IllegalArgumentException for getShort on Object field");
        } catch (IllegalArgumentException expected) {
        }

        Field fin = Holder.class.getField("fin");
        try {
            fin.getShort(h);
            throw new AssertionError("Expected IllegalArgumentException for getShort on final Object field");
        } catch (IllegalArgumentException expected) {
        }

        try {
            ref.getShort(null);
            throw new AssertionError("Expected IllegalArgumentException for getShort with null receiver");
        } catch (IllegalArgumentException expected) {
        }
    }
}
