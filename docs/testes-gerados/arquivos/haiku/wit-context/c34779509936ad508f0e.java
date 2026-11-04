/*
 * @test
 * @summary Verifies that the private no-argument PSSParameterSpec constructor always throws a RuntimeException with the documented message
 * @run main/othervm --add-opens java.base/java.security.spec=ALL-UNNAMED PSSParameterSpecWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.spec.PSSParameterSpec;

public class PSSParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        Constructor<PSSParameterSpec> ctor = PSSParameterSpec.class.getDeclaredConstructor();
        ctor.setAccessible(true);
        try {
            ctor.newInstance();
            throw new AssertionError("private no-arg constructor did not throw");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause == null || cause.getClass() != RuntimeException.class) {
                throw new AssertionError("unexpected cause: " + cause, cause);
            }
            if (!"default constructor not allowed".equals(cause.getMessage())) {
                throw new AssertionError("unexpected message: " + cause.getMessage());
            }
        }
    }
}
