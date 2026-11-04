/*
 * @test
 * @summary Test MethodHandle.asVarargsCollector behavior
 * @run main MethodHandleWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Objects;

public class MethodHandleWitupTest {

    // Minimal subclass to test asVarargsCollector
    static class DummyMethodHandle extends MethodHandle {
        private final boolean varargsCollector;
        private final boolean lastMatchResult;

        DummyMethodHandle(MethodType type, boolean varargsCollector, boolean lastMatchResult) {
            super(type);
            this.varargsCollector = varargsCollector;
            this.lastMatchResult = lastMatchResult;
        }

        @Override
        public boolean isVarargsCollector() {
            return varargsCollector;
        }

        // Simulate asCollectorChecks behavior
        boolean asCollectorChecks(Class<?> arrayType, int paramIndex, int dummy) {
            return lastMatchResult;
        }
    }

    // We need to override asVarargsCollector to call DummyMethodHandle.asCollectorChecks
    static class TestMethodHandle extends DummyMethodHandle {
        TestMethodHandle(MethodType type, boolean varargsCollector, boolean lastMatchResult) {
            super(type, varargsCollector, lastMatchResult);
        }

        @Override
        public MethodHandle asVarargsCollector(Class<?> arrayType) {
            Objects.requireNonNull(arrayType);
            boolean lastMatch = asCollectorChecks(arrayType, type().parameterCount() - 1, 0);
            if (isVarargsCollector() && lastMatch)
                return this;
            // For testing, return a new DummyMethodHandle with varargsCollector true
            return new DummyMethodHandle(type(), true, true);
        }
    }

    public static void main(String[] args) throws Throwable {
        // Test 1: arrayType null should throw NullPointerException
        try {
            MethodHandle mh = new TestMethodHandle(MethodType.methodType(void.class), false, false);
            mh.asVarargsCollector(null);
            throw new Error("Expected NullPointerException for null arrayType");
        } catch (NullPointerException expected) {
        }

        // Test 2: isVarargsCollector() == true and lastMatch == true returns same instance
        MethodHandle mh2 = new TestMethodHandle(MethodType.methodType(void.class, String[].class), true, true);
        MethodHandle result2 = mh2.asVarargsCollector(String[].class);
        if (result2 != mh2) {
            throw new Error("Expected same instance when isVarargsCollector and lastMatch are true");
        }

        // Test 3: isVarargsCollector() == false or lastMatch == false returns new instance
        MethodHandle mh3a = new TestMethodHandle(MethodType.methodType(void.class, String[].class), false, true);
        MethodHandle result3a = mh3a.asVarargsCollector(String[].class);
        if (result3a == mh3a) {
            throw new Error("Expected new instance when isVarargsCollector is false");
        }

        MethodHandle mh3b = new TestMethodHandle(MethodType.methodType(void.class, String[].class), true, false);
        MethodHandle result3b = mh3b.asVarargsCollector(String[].class);
        if (result3b == mh3b) {
            throw new Error("Expected new instance when lastMatch is false");
        }

        // Test 4: arrayType non-null accepted
        MethodHandle mh4 = new TestMethodHandle(MethodType.methodType(void.class, Object[].class), false, false);
        MethodHandle result4 = mh4.asVarargsCollector(Object[].class);
        if (result4 == null) {
            throw new Error("Expected non-null result for valid arrayType");
        }

        System.out.println("All tests passed.");
    }
}
