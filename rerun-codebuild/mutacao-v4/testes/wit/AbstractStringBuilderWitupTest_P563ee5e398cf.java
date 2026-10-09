/* @test
   @summary Testa o método codePointBefore de AbstractStringBuilder para condições de exceção.
   @run main AbstractStringBuilderWitupTest_P563ee5e398cf
 */
import java.lang.StringIndexOutOfBoundsException;
public class AbstractStringBuilderWitupTest_P563ee5e398cf {
    public static void main(String[] args) {
        testCodePointBeforeWithInvalidIndex();
    }
    private static void testCodePointBeforeWithInvalidIndex() {
        java.lang.AbstractStringBuilder builder = new java.lang.AbstractStringBuilder(10) {
            // implementação anônima para instanciar
        };
        // Forçar o valor de count para evitar NPE
        try {
            java.lang.reflect.Field countField = java.lang.AbstractStringBuilder.class.getDeclaredField("count");
            countField.setAccessible(true);
            countField.setInt(builder, 5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        int[] invalidIndices = {-1, 6};
        for (int index : invalidIndices) {
            try {
                builder.codePointBefore(index);
                throw new AssertionError("Expected StringIndexOutOfBoundsException for index: " + index);
            } catch (StringIndexOutOfBoundsException e) {
                // esperado
            }
        }
    }
}
