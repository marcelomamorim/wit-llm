/*
 * @test
 * @summary Testa o construtor PrintWriter(String, Charset) para comportamento observável.
 * @run main PrintWriterWitupTest
 */

import java.io.PrintWriter;
import java.io.IOException;
import java.nio.charset.Charset;

public class PrintWriterWitupTest {

    public static void main(String[] args) {
        testNullCharset();
        testValidCharset();
    }

    private static void testNullCharset() {
        try {
            new PrintWriter("dummy.txt", (Charset) null);
            System.err.println("Falha: Esperado NullPointerException ao passar charset null.");
        } catch (NullPointerException e) {
            System.out.println("Sucesso: NullPointerException capturada ao passar charset null.");
        } catch (Exception e) {
            System.err.println("Falha: Exceção inesperada: " + e);
        }
    }

    private static void testValidCharset() {
        try {
            Charset cs = Charset.forName("UTF-8");
            PrintWriter pw = new PrintWriter("dummy.txt", cs);
            if (pw != null) {
                System.out.println("Sucesso: PrintWriter criado com charset válido.");
                pw.close();
            } else {
                System.err.println("Falha: PrintWriter retornou null com charset válido.");
            }
        } catch (Exception e) {
            System.err.println("Falha: Exceção ao criar PrintWriter com charset válido: " + e);
        }
    }
}
