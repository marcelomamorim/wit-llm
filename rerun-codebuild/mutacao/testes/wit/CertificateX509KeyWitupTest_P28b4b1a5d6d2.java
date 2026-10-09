/*
 * @test
 * @summary Test for sun.security.x509.CertificateX509Key.get(String)
 * @run main CertificateX509KeyWitupTest_P28b4b1a5d6d2
 */

import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest_P28b4b1a5d6d2 {

    public static void main(String[] args) throws Exception {
        testGetWithValidKey();
        testGetWithInvalidKey();
    }

    private static void testGetWithValidKey() throws Exception {
        // Cria um objeto PublicKey fictício para teste
        PublicKey mockKey = new PublicKey() {
            @Override
            public String getAlgorithm() { return "RSA"; }
            @Override
            public String getFormat() { return "X.509"; }
            @Override
            public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
        };
        CertificateX509Key certKey = new CertificateX509Key(mockKey);
        // Testa o método get com o nome "value" (que deve retornar a chave)
        PublicKey result = certKey.get("value");
        if (result != mockKey) {
            throw new AssertionError("Expected the same PublicKey instance.");
        }
        // Testa o método get com o nome em maiúsculas
        result = certKey.get("VALUE");
        if (result != mockKey) {
            throw new AssertionError("Expected the same PublicKey instance.");
        }
    }

    private static void testGetWithInvalidKey() {
        try {
            // Cria um objeto PublicKey fictício para teste
            PublicKey mockKey = new PublicKey() {
                @Override
                public String getAlgorithm() { return "RSA"; }
                @Override
                public String getFormat() { return "X.509"; }
                @Override
                public byte[] getEncoded() { return new byte[] {1, 2, 3}; }
            };
            CertificateX509Key certKey = new CertificateX509Key(mockKey);
            // Chama get com um nome inválido
            certKey.get("invalid");
            throw new AssertionError("Expected IOException for invalid attribute name.");
        } catch (IOException e) {
            if (!e.getMessage().contains("Attribute name not recognized")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
