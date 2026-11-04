/*
 * @test
 * @summary LegacyAlgorithmConstraints.permits(Set, Key) always returns true
 * @modules java.base/sun.security.util
 * @run main LegacyAlgorithmConstraintsWitupTest
 */

import java.security.CryptoPrimitive;
import java.security.Key;
import java.util.Collections;
import java.util.Set;
import sun.security.util.LegacyAlgorithmConstraints;

public class LegacyAlgorithmConstraintsWitupTest {

    public static void main(String[] args) {
        LegacyAlgorithmConstraints constraints = new LegacyAlgorithmConstraints(
                LegacyAlgorithmConstraints.PROPERTY_TLS_LEGACY_ALGS, null);

        Key nullKey = null;

        if (!constraints.permits(null, nullKey)) {
            throw new RuntimeException("permits(null, null) should return true");
        }

        Set<CryptoPrimitive> empty = Collections.<CryptoPrimitive>emptySet();
        if (!constraints.permits(empty, nullKey)) {
            throw new RuntimeException("permits(empty, null) should return true");
        }

        Set<CryptoPrimitive> single =
                Collections.singleton(CryptoPrimitive.SIGNATURE);
        if (!constraints.permits(single, nullKey)) {
            throw new RuntimeException("permits(single, null) should return true");
        }

        if (!constraints.permits(single, nullKey)) {
            throw new RuntimeException("permits result should be stable");
        }
    }
}
