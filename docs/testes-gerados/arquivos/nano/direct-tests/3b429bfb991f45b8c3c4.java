/* @test    @summary Test MGF1ParameterSpec constructor with valid and null input    @run main MGF1ParameterSpecWitupTest */

import java.security.spec.MGF1ParameterSpec;

public class MGF1ParameterSpecWitupTest {

    public static void main(String[] args) {
        testValidDigest("SHA-1");
        testValidDigest("SHA-224");
        testValidDigest("SHA-256");
        testValidDigest("SHA-384");
        testValidDigest("SHA-512");
        testValidDigest("SHA-512/224");
        testValidDigest("SHA-512/256");
        testNullDigest();
    }

    private static void testValidDigest(String mdName) {
        try {
            MGF1ParameterSpec spec = new MGF1ParameterSpec(mdName);
            if (!spec.mdName.equals(mdName)) {
                throw new AssertionError("Expected mdName to be " + mdName + " but was " + spec.mdName);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for mdName: " + mdName, e);
        }
    }

    private static void testNullDigest() {
        try {
            new MGF1ParameterSpec(null);
            throw new AssertionError("Expected NullPointerException for null mdName");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for null mdName", e);
        }
    }
}
