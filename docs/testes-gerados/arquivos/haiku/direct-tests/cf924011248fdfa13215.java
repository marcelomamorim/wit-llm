/*
 * @test
 * @summary Verify PolicyChecker.init rejects forward checking and accepts reverse checking
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
        Class<?> cls = Class.forName("sun.security.provider.certpath.PolicyChecker");
        Class<?> nodeCls = Class.forName("sun.security.provider.certpath.PolicyNodeImpl");
        Constructor<?> ctor = cls.getDeclaredConstructor(Set.class, int.class,
                boolean.class, boolean.class, boolean.class, boolean.class, nodeCls);
        ctor.setAccessible(true);
        Object checker = ctor.newInstance(new HashSet<String>(), 2,
                false, false, false, false, null);

        Method init = cls.getDeclaredMethod("init", boolean.class);
        init.setAccessible(true);

        try {
            init.invoke(checker, true);
            throw new AssertionError("expected CertPathValidatorException for forward=true");
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (!(cause instanceof CertPathValidatorException)) {
                throw new AssertionError("unexpected cause: " + cause);
            }
            if (!"forward checking not supported".equals(cause.getMessage())) {
                throw new AssertionError("unexpected message: " + cause.getMessage());
            }
        }

        init.invoke(checker, false);
    }
}
