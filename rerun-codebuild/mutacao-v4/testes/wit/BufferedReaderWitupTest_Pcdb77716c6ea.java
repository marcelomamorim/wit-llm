/* @test
   @summary Testa o método skip de BufferedReader para entradas negativas.
   @run main BufferedReaderWitupTest_Pcdb77716c6ea
 */
import java.io.BufferedReader;
import java.io.StringReader;
public class BufferedReaderWitupTest_Pcdb77716c6ea {
    public static void main(String[] args) throws Exception {
        StringReader sr = new StringReader("linha1\nlinha2\nlinha3");
        BufferedReader br = new BufferedReader(sr);
        long result = br.skip(-1);
        if (result != 0) {
            throw new AssertionError("Esperado resultado 0 para skip negativo, mas foi " + result);
        }
        System.out.println("Teste de skip negativo passou, resultado: " + result);
    }
}
