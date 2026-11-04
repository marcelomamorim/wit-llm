/* @test    @summary Test ConstantCallSite.dynamicInvoker() returns the same as getTarget()    @run main ConstantCallSiteWitupTest */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class ConstantCallSiteWitupTest {

    public static void main(String[] args) throws Throwable {
        MethodHandle dummyHandle = MethodHandleImpl.DUMMY;
        ConstantCallSite ccs = new ConstantCallSite(dummyHandle);
        MethodHandle result = ccs.dynamicInvoker();
        assert result == dummyHandle : "dynamicInvoker() should return the same as getTarget()";
    }

    static class MethodHandleImpl {
        static final MethodHandle DUMMY = createDummyHandle();

        static MethodHandle createDummyHandle() {
            return new MethodHandle() {
                @Override
                public String toString() {
                    return "dummy";
                }
            };
        }
    }
}
