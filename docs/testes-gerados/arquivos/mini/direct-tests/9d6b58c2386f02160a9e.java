/*
 * @test
 * @summary Test getDefDSASubprimeSize method of SecurityProviderConstants
 * @run main SecurityProviderConstantsWitupTest
 */

import java.security.InvalidParameterException;
import sun.security.util.SecurityProviderConstants;

public class SecurityProviderConstantsWitupTest {
    public static void main(String[] args) {
        testValidSizes();
        testInvalidSize();
    }

    private static void testValidSizes() {
        // primeSize <= 1024 returns 160
        int result = SecurityProviderConstants.getDefDSASubprimeSize(1024);
        if (result != 160) {
            throw new AssertionError("Expected 160 for primeSize 1024, got " + result);
        }
        // primeSize == 2048 returns 224
        result = SecurityProviderConstants.getDefDSASubprimeSize(2048);
        if (result != 224) {
            throw new AssertionError("Expected 224 for primeSize 2048, got " + result);
        }
        // primeSize == 3072 returns 256
        result = SecurityProviderConstants.getDefDSASubprimeSize(3072);
        if (result != 256) {
            throw new AssertionError("Expected 256 for primeSize 3072, got " + result);
        }
        // primeSize less than 1024 (e.g. 512) returns 160
        result = SecurityProviderConstants.getDefDSASubprimeSize(512);
        if (result != 160) {
            throw new AssertionError("Expected 160 for primeSize 512, got " + result);
        }
    }

    private static void testInvalidSize() {
        try {
            SecurityProviderConstants.getDefDSASubprimeSize(1234);
            throw new AssertionError("Expected InvalidParameterException for primeSize 1234");
        } catch (InvalidParameterException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("1234")) {
                throw new AssertionError("Exception message does not contain invalid prime size: " + msg);
            }
        }
        try {
            SecurityProviderConstants.getDefDSASubprimeSize(0);
            throw new AssertionError("Expected InvalidParameterException for primeSize 0");
        } catch (InvalidParameterException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("0")) {
                throw new AssertionError("Exception message does not contain invalid prime size: " + msg);
            }
        }
        try {
            SecurityProviderConstants.getDefDSASubprimeSize(-1);
            throw new AssertionError("Expected InvalidParameterException for primeSize -1");
        } catch (InvalidParameterException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("-1")) {
                throw new AssertionError("Exception message does not contain invalid prime size: " + msg);
            }
        }
    }
}
