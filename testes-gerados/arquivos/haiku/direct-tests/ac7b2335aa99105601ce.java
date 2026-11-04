/*
 * @test
 * @summary ConstantCallSite.setTarget must always throw UnsupportedOperationException and keep the original target
 * @run main ConstantCallSiteWitupTest
 */

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class ConstantCallSiteWitupTest {
    public static void main(String[] args) throws Throwable {
        MethodHandle original = MethodHandles.constant(String.class, "a");
        ConstantCallSite cs = new ConstantCallSite(original);

        checkThrowsUOE(cs, MethodHandles.constant(String.class, "b"));
        checkThrowsUOE(cs, null);

        Object value = cs.getTarget().invoke();
        if (!"a".equals(value)) {
            throw new RuntimeException("target changed after setTarget: " + value);
        }
    }

    private static void checkThrowsUOE(ConstantCallSite cs, MethodHandle mh) {
        try {
            cs.setTarget(mh);
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("expected UnsupportedOperationException");
    }
}
