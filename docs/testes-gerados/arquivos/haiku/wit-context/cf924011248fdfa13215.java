/*
 * @test
 * @summary Checks PolicyChecker.init(boolean) forward-checking rejection and normal initialization via reflection
 * @modules java.base/sun.security.provider.certpath
 * @run main/othervm --add-opens java.base/sun.security.provider.certpath=ALL-UNNAMED PolicyCheckerWitupTest
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.CertPathValidatorException;
import java.util.HashSet;
import java.util.Set;

public class PolicyCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> checkerClass = Class.forName("sun.security.provider.certpath.PolicyChecker");
        Class<?> nodeClass = Class.forName("sun.security.provider.certpath.PolicyNodeImpl");

        Constructor<?> ctor = checkerClass.getDeclaredConstructor(
                Set.class, int.class, boolean.class, boolean.class,
                boolean.class, boolean.class, nodeClass);
        ctor.setAccessible(true);

        Set<String> policies = new HashSet<>();
        policies.add("2.5.29.32.0");
        Object checker = ctor.newInstance(policies, 1, false, false, false, false, null);

        Method init = checkerClass.getMethod("init", boolean.class);
        init.setAccessible(true);

        testForwardRejected(init, checker);
        testBackwardAccepted(init, checker);
    }

    private static void testForwardRejected(Method init, Object checker) throws Exception {
        try {
            init.invoke(checker, true);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof CertPathValidatorException)) {
                throw new AssertionError("Unexpected cause: " + cause, cause);
            }
            if (!"forward checking not supported".equals(cause.getMessage())) {
                throw new AssertionError("Unexpected message: " + cause.getMessage());
            }
            return;
        }
        throw new AssertionError("Expected CertPathValidatorException for forward=true");
    }

    private static void testBackwardAccepted(Method init, Object checker) throws Exception {
        try {
            init.invoke(checker, false);
        } catch (InvocationTargetException e) {
            throw new AssertionError("init(false) must not throw", e.getCause());
        }
    }
}
