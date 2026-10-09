/* @test
   @summary Testa o método write(String, int, int) de StringWriter para condições de exceção.
   @run main StringWriterWitupTest_Pc87f9ea34662
 */
import java.io.StringWriter;
public class StringWriterWitupTest_Pc87f9ea34662 {
    public static void main(String[] args) {
        testWriteWithInvalidRange();
        testWriteWithValidRange();
    }
    private static void testWriteWithInvalidRange() {
        StringWriter sw = new StringWriter();
        String str = "abc";
        try {
            sw.write(str, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
        try {
            sw.write(str, 0, 4);
            throw new AssertionError("Expected IndexOutOfBoundsException for len exceeding string length");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
        try {
            sw.write(str, 2, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
        try {
            sw.write(str, 1, 3);
            // comportamento válido, não lança
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Não esperado para range válido");
        }
    }
    private static void testWriteWithValidRange() {
        StringWriter sw = new StringWriter();
        String str = "abc";
        sw.write(str, 0, 3);
        if (!sw.toString().equals("abc")) {
            throw new AssertionError("Conteúdo esperado: 'abc'");
        }
        sw = new StringWriter();
        sw.write(str, 1, 2);
        if (!sw.toString().equals("bc")) {
            throw new AssertionError("Conteúdo esperado: 'bc'");
        }
    }
}
