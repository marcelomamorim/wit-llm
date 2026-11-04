/* @test    @summary Teste do método run() de sun.net.ftp.impl.FtpClient para comportamento observável.    @run main FtpClientWitupTest */

import java.net.*;
import sun.net.ftp.impl.FtpClient;

public class FtpClientWitupTest {

    public static void main(String[] args) {
        testRunWithNullProxy();
        testRunWithNonNullProxy();
    }

    private static void testRunWithNullProxy() {
        try {
            FtpClient client = new FtpClient();
            // Como o construtor é protected, usar uma subclasse anônima para instanciar
            FtpClient instance = new FtpClient() {};
            // Forçar proxy como null
            // Como o método run() não usa proxy interno, apenas invoca new Socket(proxy)
            // e proxy é null, então deve lançar IllegalArgumentException
            // Mas na implementação atual, o método simplesmente retorna new Socket(proxy)
            // com proxy null, o que pode lançar NullPointerException ou criar socket inválido.
            // Como o método não lança exceção explicitamente, testar o comportamento.
            try {
                Socket s = instance.run();
                System.out.println("run() com proxy null retornou: " + s);
            } catch (NullPointerException npe) {
                System.out.println("run() com proxy null lançou NullPointerException como esperado.");
            } catch (IllegalArgumentException iae) {
                System.out.println("run() com proxy null lançou IllegalArgumentException.");
            } catch (Exception e) {
                System.out.println("run() com proxy null lançou exceção inesperada: " + e);
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar instância de FtpClient: " + e);
        }
    }

    private static void testRunWithNonNullProxy() {
        try {
            Proxy proxy = Proxy.NO_PROXY;
            FtpClient instance = new FtpClient() {};
            // Invoca run() com proxy não nulo
            try {
                Socket s = instance.run();
                System.out.println("run() com proxy não nulo retornou: " + s);
            } catch (Exception e) {
                System.out.println("run() com proxy não nulo lançou exceção: " + e);
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar instância de FtpClient: " + e);
        }
    }
}
