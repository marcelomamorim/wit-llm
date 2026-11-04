/* @test    @summary Teste do construtor OCSPNonceExtension(byte[] incomingNonce) para verificar comportamento com entradas nulas e inválidas    @run main OCSPNonceExtensionWitupTest */

import sun.security.provider.certpath.OCSPNonceExtension;
import java.io.IOException;

public class OCSPNonceExtensionWitupTest {

    public static void main(String[] args) throws IOException {
        testNullIncomingNonce();
        testEmptyIncomingNonce();
    }

    private static void testNullIncomingNonce() {
        try {
            new OCSPNonceExtension((byte[]) null);
            System.out.println("Falha: não lançou NullPointerException com entrada nula");
        } catch (NullPointerException e) {
            System.out.println("Sucesso: NullPointerException lançada com entrada nula");
        } catch (Exception e) {
            System.out.println("Falha: exceção inesperada: " + e);
        }
    }

    private static void testEmptyIncomingNonce() {
        try {
            byte[] empty = new byte[0];
            new OCSPNonceExtension(empty);
            System.out.println("Falha: não lançou IllegalArgumentException com array vazio");
        } catch (IllegalArgumentException e) {
            System.out.println("Sucesso: IllegalArgumentException lançada com array vazio");
        } catch (Exception e) {
            System.out.println("Falha: exceção inesperada: " + e);
        }
    }
}
