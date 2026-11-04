/* @test    @summary Test getDefDSASubprimeSize with valid and invalid inputs    @run main SecurityProviderConstantsWitupTest */

import sun.security.util.SecurityProviderConstants;
import java.security.InvalidParameterException;

public class SecurityProviderConstantsWitupTest {

    public static void main(String[] args) {
        testGetDefDSASubprimeSize();
    }

    private static void testGetDefDSASubprimeSize() {
        // Test with primeSize <= 1024
        int result1 = SecurityProviderConstants.getDefDSASubprimeSize(1024);
        assert result1 == 160 : "Expected 160 for primeSize 1024, but got " + result1;

        // Test with primeSize == 2048
        int result2 = SecurityProviderConstants.getDefDSASubprimeSize(2048);
        assert result2 == 224 : "Expected 224 for primeSize 2048, but got " + result2;

        // Test with primeSize == 3072
        int result3 = SecurityProviderConstants.getDefDSASubprimeSize(3072);
        assert result3 == 256 : "Expected 256 for primeSize 3072, but got " + result3;

        // Test with invalid primeSize (e.g., 4096) should throw InvalidParameterException
        boolean exceptionThrown = false;
        try {
            SecurityProviderConstants.getDefDSASubprimeSize(4096);
        } catch (InvalidParameterException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected InvalidParameterException for primeSize 4096";
    }
}
