/* @test    @summary Test permits method of LegacyAlgorithmConstraints always returns true    @run main LegacyAlgorithmConstraintsWitupTest */

import sun.security.util.LegacyAlgorithmConstraints;
import java.security.Key;
import java.util.Set;

public class LegacyAlgorithmConstraintsWitupTest {

    public static void main(String[] args) {
        testPermitsAlwaysReturnsTrue();
    }

    private static void testPermitsAlwaysReturnsTrue() {
        LegacyAlgorithmConstraints constraints = new LegacyAlgorithmConstraints("dummyProperty", null);
        Key dummyKey = new Key() {
            @Override
            public String getAlgorithm() {
                return "DummyAlgorithm";
            }
            @Override
            public String getFormat() {
                return "";
            }
            @Override
            public byte[] getEncoded() {
                return new byte[0];
            }
        };
        boolean result = constraints.permits(Set.of(), dummyKey);
        if (!result) {
            throw new AssertionError("permits should always return true");
        }
    }
}
