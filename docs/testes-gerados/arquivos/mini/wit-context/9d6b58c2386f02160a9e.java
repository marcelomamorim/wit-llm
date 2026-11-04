/*
 * @test
 * @summary Test sun.security.util.SecurityProviderConstants.getDefDSASubprimeSize(int) method
 * @run main SecurityProviderConstantsWitupTest
 */

import java.security.InvalidParameterException;
import sun.security.util.SecurityProviderConstants;

public class SecurityProviderConstantsWitupTest {
    public static void main(String[] args) {
        testValidPrimeSizes();
        testInvalidPrimeSizes();
        System.out.println("All tests passed.");
    }

    private static void testValidPrimeSizes() {
        // primeSize <= 1024 returns 160
        if (SecurityProviderConstants.getDefDSASubprimeSize(1024) != 160) {
            throw new AssertionError("Expected 160 for primeSize 1024");
        }
        if (SecurityProviderConstants.getDefDSASubprimeSize(512) != 160) {
            throw new AssertionError("Expected 160 for primeSize 512");
        }

        // primeSize == 2048 returns 224
        if (SecurityProviderConstants.getDefDSASubprimeSize(2048) != 224) {
            throw new AssertionError("Expected 224 for primeSize 2048");
        }

        // primeSize == 3072 returns 256
        if (SecurityProviderConstants.getDefDSASubprimeSize(3072) != 256) {
            throw new AssertionError("Expected 256 for primeSize 3072");
        }
    }

    private static void testInvalidPrimeSizes() {
        // primeSize > 1024 and not 2048 or 3072 throws InvalidParameterException
        int[] invalidSizes = {1025, 1500, 4096, Integer.MAX_VALUE};
        for (int size : invalidSizes) {
            try {
                SecurityProviderConstants.getDefDSASubprimeSize(size);
                throw new AssertionError("Expected InvalidParameterException for primeSize " + size);
            } catch (InvalidParameterException e) {
                // expected
                String msg = e.getMessage();
                if (msg == null || !msg.contains(String.valueOf(size))) {
                    throw new AssertionError("Exception message does not contain primeSize: " + msg);
                }
            }
        }
    }
}
