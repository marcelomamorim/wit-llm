/* @test    @summary Test getDefDSASubprimeSize with valid and invalid inputs    @run main SecurityProviderConstantsWitupTest */

import sun.security.util.SecurityProviderConstants;

public class SecurityProviderConstantsWitupTest {

    public static void main(String[] args) {
        testValidPrimeSizes();
        testInvalidPrimeSize();
    }

    private static void testValidPrimeSizes() {
        int size1 = 1024;
        int size2 = 2048;
        int size3 = 3072;
        int result1 = SecurityProviderConstants.getDefDSASubprimeSize(size1);
        int result2 = SecurityProviderConstants.getDefDSASubprimeSize(size2);
        int result3 = SecurityProviderConstants.getDefDSASubprimeSize(size3);
        if (result1 != 160) {
            throw new AssertionError("Expected 160 for size 1024, got " + result1);
        }
        if (result2 != 224) {
            throw new AssertionError("Expected 224 for size 2048, got " + result2);
        }
        if (result3 != 256) {
            throw new AssertionError("Expected 256 for size 3072, got " + result3);
        }
    }

    private static void testInvalidPrimeSize() {
        int invalidSize = 3000;
        try {
            SecurityProviderConstants.getDefDSASubprimeSize(invalidSize);
            throw new AssertionError("Expected InvalidParameterException for size " + invalidSize);
        } catch (InvalidParameterException e) {
            // expected
        }
    }
}
