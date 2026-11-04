/*
 * @test
 * @summary Verifies KeyChecker.init(boolean) accepts reverse checking and rejects forward checking
 * @modules java.base/sun.security.provider.certpath
 * @run main/othervm --add-opens java.base/sun.security.provider.certpath=ALL-UNNAMED KeyCheckerWitupTest
 */

import java.lang.reflect.Constructor;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertSelector;
import java.security.cert.PKIXCertPathChecker;

public class KeyCheckerWitupTest {

    public static void main(String[] args) throws Exception {
        Class<?> c = Class.forName("sun.security.provider.certpath.KeyChecker");
        Constructor<?> ctor = c.getDeclaredConstructor(int.class, CertSelector.class);
        ctor.setAccessible(true);

        PKIXCertPathChecker checker = (PKIXCertPathChecker) ctor.newInstance(2, null);

        if (checker.isForwardCheckingSupported()) {
            throw new RuntimeException("forward checking must not be supported");
        }

        checker.init(false);
        checker.init(false);

        boolean thrown = false;
        try {
            checker.init(true);
        } catch (CertPathValidatorException e) {
            thrown = true;
            if (!"forward checking not supported".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("init(true) must throw CertPathValidatorException");
        }
    }
}
