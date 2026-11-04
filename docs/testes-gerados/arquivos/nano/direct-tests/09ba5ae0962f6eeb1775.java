/*
 * @test
 * @summary Test ConstantCallSite.getTarget() for frozen and unfrozen states
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.ConstantCallSite;

public class ConstantCallSiteWitupTest {

    public static void main(String[] args) {
        testGetTargetFrozen();
        testGetTargetUnfrozen();
    }

    static void testGetTargetFrozen() {
        MethodHandle mh = createDummyMethodHandle();
        ConstantCallSite ccs = new ConstantCallSite(mh);
        MethodHandle result = ccs.getTarget();
        assert result == mh : "Expected target to be the same as the one provided";
    }

    static void testGetTargetUnfrozen() {
        MethodType type = MethodType.methodType(void.class);
        MethodHandle hook = createDummyMethodHandle();
        try {
            ConstantCallSite ccs = new ConstantCallSite(type, hook);
            // Since isFrozen is true after construction, getTarget() should work
            MethodHandle result = ccs.getTarget();
            assert result == hook : "Expected target to be the hook method handle";
        } catch (Throwable t) {
            // If construction throws, fail the test
            throw new AssertionError("Unexpected exception during construction", t);
        }
    }

    static MethodHandle createDummyMethodHandle() {
        // Create a dummy MethodHandle that does nothing
        // Since we cannot instantiate MethodHandle directly, use a dummy subclass
        return new DummyMethodHandle();
    }

    static class DummyMethodHandle extends MethodHandle {
        protected DummyMethodHandle() {
            super(null, null, null);
        }
        @Override
        public String toString() {
            return "DummyMethodHandle";
        }
    }
}
