/* @test
   @summary Testa o método mark() de BufferedReader para entradas inválidas.
   @run main BufferedReaderWitupTest_P61ac87410b78
 */
import java.io.BufferedReader;
import java.io.StringReader;
public class BufferedReaderWitupTest_P61ac87410b78 {
    public static void main(String[] args) {
        StringReader sr = new StringReader("linha1\nlinha2");
        BufferedReader br = new BufferedReader(sr);
        try {
            br.mark(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative readAheadLimit");
        } catch (IllegalArgumentException e) {
            // esperado
        }
        try {
            br.mark(0);
            // comportamento esperado: não lança exceção para zero
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for zero readAheadLimit", e);
        }
    }
}
