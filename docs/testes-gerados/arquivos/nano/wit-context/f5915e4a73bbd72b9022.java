/* @test    @summary Teste do método delete de OCSPNoCheckExtension para comportamento esperado de exceção.    @run main OCSPNoCheckExtensionWitupTest */

import java.io.IOException;
import sun.security.x509.OCSPNoCheckExtension;

public class OCSPNoCheckExtensionWitupTest {

    public static void main(String[] args) {
        testDeleteThrowsIOException();
    }

    public static void testDeleteThrowsIOException() {
        OCSPNoCheckExtension ext = null;
        try {
            ext = new OCSPNoCheckExtension();
        } catch (IOException e) {
            throw new RuntimeException("Falha na criação da extensão", e);
        }
        try {
            ext.delete("anyName");
            System.out.println("Erro: esperado IOException não lançada.");
            System.exit(1);
        } catch (IOException e) {
            if (e.getMessage().contains("No attribute is allowed")) {
                System.out.println("Teste bem-sucedido: IOException lançada com mensagem esperada.");
            } else {
                System.out.println("Erro: mensagem de exceção inesperada: " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
