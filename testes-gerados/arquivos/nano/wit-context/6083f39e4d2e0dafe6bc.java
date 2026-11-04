/* @test    @summary Test for LegacyAlgorithmConstraints.permits(Set<CryptoPrimitive> primitives, Key key) method
 * @run main LegacyAlgorithmConstraintsWitupTest
 */

import sun.security.util.LegacyAlgorithmConstraints;
import java.security.Key;
import java.util.Set;

public class LegacyAlgorithmConstraintsWitupTest {

    public static void main(String[] args) {
        testPermitsAlwaysReturnsTrue();
    }

    private static void testPermitsAlwaysReturnsTrue() {
        // Cria uma instância de LegacyAlgorithmConstraints com valores fictícios
        LegacyAlgorithmConstraints constraints = new LegacyAlgorithmConstraints("dummyProperty", null);
        // Chamada do método com argumentos fictícios
        boolean result = constraints.permits(null, null);
        // Verifica se o resultado é true
        if (result != true) {
            throw new AssertionError("Expected permits to return true, but got " + result);
        }
    }
}
