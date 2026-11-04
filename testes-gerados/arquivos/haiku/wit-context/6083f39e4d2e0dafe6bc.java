/*
 * @test
 * @summary Witup test for LegacyAlgorithmConstraints.permits(Set, Key), which always returns true
 * @modules java.base/sun.security.util
 * @run main LegacyAlgorithmConstraintsWitupTest
 */

import java.security.CryptoPrimitive;
import java.security.Key;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import sun.security.util.LegacyAlgorithmConstraints;

public class LegacyAlgorithmConstraintsWitupTest {

    private static final class TestKey implements Key {
        private static final long serialVersionUID = 1L;

        @Override
        public String getAlgorithm() {
            return null;
        }

        @Override
        public String getFormat() {
            return null;
        }

        @Override
        public byte[] getEncoded() {
            return new byte[0];
        }
    }

    public static void main(String[] args) {
        LegacyAlgorithmConstraints constraints = new LegacyAlgorithmConstraints(
                LegacyAlgorithmConstraints.PROPERTY_TLS_LEGACY_ALGS, null);

        Key key = new TestKey();

        Set<CryptoPrimitive> signature = EnumSet.of(CryptoPrimitive.SIGNATURE);
        if (!constraints.permits(signature, key)) {
            throw new RuntimeException("permits(Set, Key) must return true for a non-null key");
        }

        Set<CryptoPrimitive> empty = Collections.<CryptoPrimitive>emptySet();
        if (!constraints.permits(empty, key)) {
            throw new RuntimeException("permits(Set, Key) must return true for an empty primitive set");
        }

        if (!constraints.permits(null, null)) {
            throw new RuntimeException("permits(Set, Key) must return true for null arguments");
        }
    }
}
