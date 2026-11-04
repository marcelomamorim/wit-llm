/*
 * @test
 * @summary Verifies getDefDSASubprimeSize return values and InvalidParameterException for unsupported DSA prime sizes
 * @modules java.base/sun.security.util
 * @run main SecurityProviderConstantsWitupTest
 */

import java.security.InvalidParameterException;
import sun.security.util.SecurityProviderConstants;

public class SecurityProviderConstantsWitupTest {

    public static void main(String[] args) {
        checkSubprimeSize(0, 160);
        checkSubprimeSize(-1, 160);
        checkSubprimeSize(512, 160);
        checkSubprimeSize(1024, 160);
        checkSubprimeSize(2048, 224);
        checkSubprimeSize(3072, 256);

        expectInvalidPrimeSize(1025);
        expectInvalidPrimeSize(2049);
        expectInvalidPrimeSize(3071);
        expectInvalidPrimeSize(3073);
        expectInvalidPrimeSize(4096);
        expectInvalidPrimeSize(Integer.MAX_VALUE);
    }

    private static void checkSubprimeSize(int primeSize, int expected) {
        int actual = SecurityProviderConstants.getDefDSASubprimeSize(primeSize);
        if (actual != expected) {
            throw new RuntimeException("For prime size " + primeSize +
                " expected " + expected + " but got " + actual);
        }
    }

    private static void expectInvalidPrimeSize(int primeSize) {
        try {
            SecurityProviderConstants.getDefDSASubprimeSize(primeSize);
        } catch (InvalidParameterException e) {
            return;
        }
        throw new RuntimeException("Expected InvalidParameterException for prime size " +
            primeSize);
    }
}
