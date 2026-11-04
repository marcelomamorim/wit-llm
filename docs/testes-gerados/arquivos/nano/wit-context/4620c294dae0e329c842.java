/* @test    @summary Test for DisabledAlgorithmConstraints.permits(Key key) method.
 * @run main DisabledAlgorithmConstraintsWitupTest
 */

import sun.security.util.DisabledAlgorithmConstraints;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.KeyPairGenerator;
import java.security.KeyPair;

public class DisabledAlgorithmConstraintsWitupTest {

    public static void main(String[] args) throws Exception {
        testPermitsReturnsFalse();
    }

    private static void testPermitsReturnsFalse() throws NoSuchAlgorithmException {
        // Geração de uma chave para passar como argumento
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Key key = kp.getPublic();

        // Instancia do objeto com propriedade qualquer (não importa o valor real)
        DisabledAlgorithmConstraints dac = new DisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms");

        // Chamada do método e verificação do retorno esperado
        boolean result = dac.permits(key);
        if (result != false) {
            throw new AssertionError("Expected permits to return false, but got true.");
        }
    }
}
