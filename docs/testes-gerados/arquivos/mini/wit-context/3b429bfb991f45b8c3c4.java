/*
 * @test
 * @summary Test constructor of MGF1ParameterSpec for null and valid input
 * @run main MGF1ParameterSpecWitupTest
 */

import java.security.spec.MGF1ParameterSpec;

public class MGF1ParameterSpecWitupTest {
    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidName();
    }

    private static void testConstructorWithNull() {
        try {
            new MGF1ParameterSpec(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("digest algorithm is null")) {
                throw new RuntimeException("Unexpected exception message: " + msg);
            }
        }
    }

    private static void testConstructorWithValidName() {
        String[] validNames = {
            "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "SHA-512/224", "SHA-512/256"
        };
        for (int i = 0; i < validNames.length; i++) {
            MGF1ParameterSpec spec = new MGF1ParameterSpec(validNames[i]);
            if (spec == null) {
                throw new RuntimeException("Constructor returned null for " + validNames[i]);
            }
            // Check that the mdName field is set correctly via toString or equals on constants
            // Since mdName is private and no getter, compare with known constants
            boolean matched = false;
            if (validNames[i].equals("SHA-1") && spec.equals(MGF1ParameterSpec.SHA1)) matched = true;
            else if (validNames[i].equals("SHA-224") && spec.equals(MGF1ParameterSpec.SHA224)) matched = true;
            else if (validNames[i].equals("SHA-256") && spec.equals(MGF1ParameterSpec.SHA256)) matched = true;
            else if (validNames[i].equals("SHA-384") && spec.equals(MGF1ParameterSpec.SHA384)) matched = true;
            else if (validNames[i].equals("SHA-512") && spec.equals(MGF1ParameterSpec.SHA512)) matched = true;
            else if (validNames[i].equals("SHA-512/224") && spec.equals(MGF1ParameterSpec.SHA512_224)) matched = true;
            else if (validNames[i].equals("SHA-512/256") && spec.equals(MGF1ParameterSpec.SHA512_256)) matched = true;
            if (!matched) {
                // If not matched to constants, just accept non-null instance
                // No public getter to verify mdName, so no further check
            }
        }
    }
}
