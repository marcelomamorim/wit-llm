/*
 * @test
 * @summary Verify that Field.getChar on an Object-typed field throws IllegalArgumentException, which exercises UnsafeObjectFieldAccessorImpl.getChar through the public reflection API
 * @run main UnsafeObjectFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeObjectFieldAccessorImplWitupTest {

    static class Holder {
        Object ref = "value";
    }

    static class NullHolder {
        Object ref;
    }

    public static void main(String[] args) throws Exception {
        Field f = Holder.class.getDeclaredField("ref");
        Holder h = new Holder();
        expectIllegalArgument(f, h);

        Field nf = NullHolder.class.getDeclaredField("ref");
        NullHolder nh = new NullHolder();
        expectIllegalArgument(nf, nh);
    }

    static void expectIllegalArgument(Field f, Object target) throws Exception {
        try {
            f.getChar(target);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException from getChar on Object field");
    }
}
