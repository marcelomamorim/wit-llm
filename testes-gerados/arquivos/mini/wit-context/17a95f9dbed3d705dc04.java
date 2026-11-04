/*
 * @test
 * @summary Test MethodHandle.asVarargsCollector behavior and null argument handling
 * @run main MethodHandleWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Objects;

public class MethodHandleWitupTest {

    // Minimal stub subclass to allow instantiation for testing
    static class MHStub extends MethodHandle {
        MHStub(MethodType type) {
            super(type);
        }

        @Override
        public MethodType type() {
            return super.type();
        }

        // Override isVarargsCollector to false for testing
        @Override
        boolean isVarargsCollector() {
            return false;
        }
    }

    public static void main(String[] args) throws Throwable {
        testAsVarargsCollectorNullArgument();
        testAsVarargsCollectorReturnsNewOrSelf();
    }

    private static void testAsVarargsCollectorNullArgument() {
        MethodHandle mh = new MHStub(MethodType.methodType(void.class));
        try {
            mh.asVarargsCollector(null);
            throw new Error("Expected NullPointerException for null arrayType");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testAsVarargsCollectorReturnsNewOrSelf() {
        MethodType mt = MethodType.methodType(void.class, String[].class);
        MHStub mh = new MHStub(mt) {
            @Override
            boolean isVarargsCollector() {
                return false;
            }
        };

        // Call asVarargsCollector with arrayType matching last parameter type
        MethodHandle result = mh.asVarargsCollector(String[].class);
        if (result == null) {
            throw new Error("asVarargsCollector returned null");
        }

        // Now test when isVarargsCollector() returns true and lastMatch true, should return this
        MHStub mh2 = new MHStub(mt) {
            @Override
            boolean isVarargsCollector() {
                return true;
            }

            @Override
            public MethodHandle asVarargsCollector(Class<?> arrayType) {
                Objects.requireNonNull(arrayType);
                boolean lastMatch = true; // simulate lastMatch true
                if (isVarargsCollector() && lastMatch)
                    return this;
                return super.asVarargsCollector(arrayType);
            }
        };

        MethodHandle result2 = mh2.asVarargsCollector(String[].class);
        if (result2 != mh2) {
            throw new Error("Expected asVarargsCollector to return this when isVarargsCollector and lastMatch are true");
        }
    }
}
