/* @test    @summary Test for sun.security.x509.CertificateX509Key.get(String) method
 * @run main CertificateX509KeyWitupTest_P197b6f9b889b
 */

import java.io.IOException;
import sun.security.x509.CertificateX509Key;
import java.security.PublicKey;

public class CertificateX509KeyWitupTest_P197b6f9b889b {

    public static void main(String[] args) throws Exception {
        testGetWithValidKey();
        testGetWithInvalidKey();
    }

    private static void testGetWithValidKey() throws Exception {
        // Cria um objeto PublicKey mock ou dummy, pois não há construtor público direto, então usamos um stub
        PublicKey dummyKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        // Cria uma instância de CertificateX509Key com o key válido
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        // Invoca o método get com o nome esperado
        PublicKey result = certKey.get("value");
        if (result != dummyKey) {
            throw new AssertionError("Expected the same PublicKey instance.");
        }
        // Invoca o método get com o nome em caixa diferente
        result = certKey.get("VALUE");
        if (result != dummyKey) {
            throw new AssertionError("Expected the same PublicKey instance with case-insensitive key.");
        }
        System.out.println("PASS: testGetWithValidKey");
    }

    private static void testGetWithInvalidKey() {
        // Cria um objeto PublicKey dummy
        PublicKey dummyKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        // Cria uma instância de CertificateX509Key com o key válido
        CertificateX509Key certKey;
        try {
            certKey = new CertificateX509Key(dummyKey);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create CertificateX509Key", e);
        }
        // Tenta obter uma chave com nome inválido
        try {
            certKey.get("invalid");
            throw new AssertionError("Expected IOException for invalid attribute name");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
        System.out.println("PASS: testGetWithInvalidKey");
    }
}
