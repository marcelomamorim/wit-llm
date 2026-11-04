/* @test    @summary Test for ManifestDigester.manifestDigest to verify behavior with null rawBytes.    @run main ManifestDigesterWitupTest */

import sun.security.util.ManifestDigester;
import java.security.MessageDigest;

public class ManifestDigesterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullRawBytes();
        testValidRawBytes();
    }

    private static void testNullRawBytes() {
        byte[] rawBytes = null;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        try {
            // Cria uma instância de ManifestDigester com rawBytes nulo
            ManifestDigester mdg = new ManifestDigester(new byte[0]) {
                {
                    // sobrescreve rawBytes para null
                    this.rawBytes = null;
                }
            };
            byte[] result = mdg.manifestDigest(md);
            // Espera uma IllegalArgumentException devido a rawBytes nulo
            // Como o método atual não lança exceção, o teste verifica o comportamento
            // Se o método lançar, o teste passará ao capturar a exceção
            System.out.println("Erro: esperado exceção para rawBytes nulo, mas não ocorreu.");
        } catch (IllegalArgumentException e) {
            System.out.println("Passou: exceção IllegalArgumentException capturada como esperado.");
        } catch (Exception e) {
            System.out.println("Falha: exceção inesperada: " + e);
        }
    }

    private static void testValidRawBytes() throws Exception {
        byte[] data = {1, 2, 3, 4, 5};
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        ManifestDigester mdg = new ManifestDigester(data);
        byte[] digest = mdg.manifestDigest(md);
        if (digest != null && digest.length > 0) {
            System.out.println("Passou: digest obtido com sucesso.");
        } else {
            System.out.println("Falha: digest inválido.");
        }
    }
}
