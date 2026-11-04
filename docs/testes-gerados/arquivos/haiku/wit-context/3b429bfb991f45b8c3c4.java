/*
 * @test
 * @summary Witup tests for MGF1ParameterSpec constructor
 * @run main MGF1ParameterSpecWitupTest
 */

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;

public class MGF1ParameterSpecWitupTest {

    public static void main(String[] args) {
        testNullDigestNameThrowsNPE();
        testNonNullDigestNamesAccepted();
        testPredefinedConstantsAreNotNullAndAreSpecs();
    }

    private static void testNullDigestNameThrowsNPE() {
        boolean thrown = false;
        try {
            new MGF1ParameterSpec(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null mdName");
        }
    }

    private static void testNonNullDigestNamesAccepted() {
        String[] names = { "SHA-1", "SHA-256", "SHA-512/256", "" };
        for (String name : names) {
            MGF1ParameterSpec spec = new MGF1ParameterSpec(name);
            if (spec == null) {
                throw new RuntimeException("Constructor returned null for name: " + name);
            }
        }
    }

    private static void testPredefinedConstantsAreNotNullAndAreSpecs() {
        MGF1ParameterSpec[] constants = {
            MGF1ParameterSpec.SHA1,
            MGF1ParameterSpec.SHA224,
            MGF1ParameterSpec.SHA256,
            MGF1ParameterSpec.SHA384,
            MGF1ParameterSpec.SHA512,
            MGF1ParameterSpec.SHA512_224,
            MGF1ParameterSpec.SHA512_256
        };
        for (MGF1ParameterSpec c : constants) {
            if (c == null) {
                throw new RuntimeException("Predefined MGF1ParameterSpec constant is null");
            }
            AlgorithmParameterSpec aps = c;
            if (!(aps instanceof MGF1ParameterSpec)) {
                throw new RuntimeException("Constant is not an MGF1ParameterSpec");
            }
        }
    }
}
