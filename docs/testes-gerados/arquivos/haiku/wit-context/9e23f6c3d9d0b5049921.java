/*
 * @test
 * @summary Checks Field.getChar on a volatile char field, which exercises the Unsafe-qualified character accessor through its public API
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {

    static class Holder {
        volatile char c = 'x';
    }

    interface Action {
        void run() throws Exception;
    }

    static void expectIllegalArgument(Action action, String label) throws Exception {
        try {
            action.run();
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException: " + label);
    }

    public static void main(String[] args) throws Exception {
        final Field f = Holder.class.getDeclaredField("c");
        final Holder h = new Holder();

        if (f.getChar(h) != 'x') {
            throw new AssertionError("Initial getChar mismatch");
        }
        if (!Character.valueOf('x').equals(f.get(h))) {
            throw new AssertionError("get() should return Character 'x'");
        }

        f.setChar(h, 'z');
        if (f.getChar(h) != 'z') {
            throw new AssertionError("getChar after setChar mismatch");
        }

        expectIllegalArgument(() -> f.getChar("not a holder"), "wrong object type");
        expectIllegalArgument(() -> f.getChar(null), "null object");
    }
}
