/*
 * @test
 * @summary Tests SecurityProviderConstants.getDefDSASubprimeSize mapping and invalid prime sizes
 * @modules java.base/sun.security.util
 * @run main SecurityProviderConstantsWitupTest
 */

import java.security.InvalidParameterException;
import sun.security.util.SecurityProviderConstants;

public class SecurityProviderConstantsWitupTest {
    public static void main(String[] args) {
        check(SecurityProviderConstants.getDefDSASubprimeSize(0), 160);
        check(SecurityProviderConstants.getDefDSASubprimeSize(-1), 160);
        check(SecurityProviderConstants.getDefDSASubprimeSize(512), 160);
        check(SecurityProviderConstants.getDefDSASubprimeSize(1024), 160);
        check(SecurityProviderConstants.getDefDSASubprimeSize(2048), 224);
        check(SecurityProviderConstants.getDefDSASubprimeSize(3072), 256);

        expectInvalid(1025);
        expectInvalid(2047);
        expectInvalid(2049);
        expectInvalid(3071);
        expectInvalid(4096);
    }

    static void check(int actual, int expected) {
        if (actual != expected) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }

    static void expectInvalid(int primeSize) {
        try {
            SecurityProviderConstants.getDefDSASubprimeSize(primeSize);
        } catch (InvalidParameterException e) {
            return;
        }
        throw new RuntimeException("no InvalidParameterException for " + primeSize);
    }
}
