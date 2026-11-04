/*
 * @test
 * @summary Testa o método read(byte[] b) de HttpURLConnection para comportamento esperado.
 * @run main HttpURLConnectionWitupTest
 */

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;

public class HttpURLConnectionWitupTest {

    public static void main(String[] args) throws Exception {
        testReadWithClosedStream();
    }

    private static void testReadWithClosedStream() {
        try {
            URL url = new URL("http://example.com");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // Simula fechamento do stream interno para provocar IOException
            // Como não podemos acessar internamente, vamos forçar uma condição que cause IOException
            // por exemplo, fechando a conexão antes de leitura
            conn.disconnect();
            byte[] buffer = new byte[10];
            int result = conn.read(buffer);
            // Se não lançar exceção, o teste falha
            System.err.println("Erro: esperado IOException ao ler de conexão fechada, mas obteve resultado: " + result);
        } catch (IOException e) {
            // Esperado
            System.out.println("Passou: IOException capturada como esperado.");
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e);
        }
    }
}
