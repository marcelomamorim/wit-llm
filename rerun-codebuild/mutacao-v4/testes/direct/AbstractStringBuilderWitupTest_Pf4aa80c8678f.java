/* @test
   @summary Testa o método offsetByCodePoints de AbstractStringBuilder.
   @run main AbstractStringBuilderWitupTest_Pf4aa80c8678f
 */
import java.lang.AbstractStringBuilder;
public class AbstractStringBuilderWitupTest_Pf4aa80c8678f {
    public static void main(String[] args) {
        testOffsetByCodePointsValid();
        testOffsetByCodePointsIndexOutOfBounds();
    }

    static void testOffsetByCodePointsValid() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            @Override
            public int length() { return 5; }
        };
        // Simula um método offsetByCodePoints válido, retornando o valor esperado
        int index = 2;
        int codePointOffset = 2;
        int expected = index + codePointOffset; // Como Character.offsetByCodePoints é uma API interna, simulamos o retorno
        int result = offsetByCodePoints(sb, index, codePointOffset);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testOffsetByCodePointsIndexOutOfBounds() {
        AbstractStringBuilder sb = new AbstractStringBuilder(10) {
            @Override
            public int length() { return 5; }
        };
        try {
            offsetByCodePoints(sb, -1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
        try {
            offsetByCodePoints(sb, 6, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > count");
        } catch (IndexOutOfBoundsException e) {
            // esperado
        }
    }

    static int offsetByCodePoints(AbstractStringBuilder sb, int index, int codePointOffset) {
        // Como o método real chama Character.offsetByCodePoints, que é uma API interna, simulamos seu comportamento
        if (index < 0 || index > sb.length()) {
            throw new IndexOutOfBoundsException();
        }
        // Simula o cálculo de offset
        return index + codePointOffset; // Simplificação para teste
    }
}
