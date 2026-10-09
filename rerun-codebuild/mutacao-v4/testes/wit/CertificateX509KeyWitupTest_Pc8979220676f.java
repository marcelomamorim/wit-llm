/* @test
   @summary Testa o método set de CertificateX509Key para diferentes valores de 'name'.
   @run main CertificateX509KeyWitupTest_Pc8979220676f
 */
import java.io.IOException;
import java.security.PublicKey;
import sun.security.x509.CertificateX509Key;

public class CertificateX509KeyWitupTest_Pc8979220676f {
    public static void main(String[] args) throws Exception {
        testSetWithValidKey();
        testSetWithInvalidName();
    }

    private static void testSetWithValidKey() throws Exception {
        PublicKey dummyKey = null; // não é necessário um objeto válido para o teste de fluxo
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        try {
            certKey.set("value", dummyKey);
        } catch (IOException e) {
            throw new AssertionError("Não deveria lançar IOException ao usar " +
                "nome 'value'", e);
        }
    }

    private static void testSetWithInvalidName() {
        PublicKey dummyKey = null; // não é necessário um objeto válido para o teste de fluxo
        CertificateX509Key certKey = new CertificateX509Key(dummyKey);
        boolean thrown = false;
        try {
            certKey.set("invalidName", new Object());
            throw new AssertionError("Esperava IOException ao usar nome inválido");
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Falha: IOException não foi lançada para nome inválido");
        }
    }
}
