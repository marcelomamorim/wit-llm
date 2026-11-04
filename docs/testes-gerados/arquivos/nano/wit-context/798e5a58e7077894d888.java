/*
 * @test
 * @summary Test for StringJoiner.setEmptyValue(CharSequence)
 * @run main StringJoinerWitupTest
 */

import java.util.StringJoiner;

public class StringJoinerWitupTest {

    public static void main(String[] args) {
        testSetEmptyValueWithNull();
        testSetEmptyValueWithValidString();
    }

    private static void testSetEmptyValueWithNull() {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        try {
            sj.setEmptyValue(null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException for null emptyValue");
        } catch (NullPointerException e) {
            // Esperado
        }
    }

    private static void testSetEmptyValueWithValidString() {
        StringJoiner sj = new StringJoiner(",", "<", ">");
        String testString = "empty";
        sj.setEmptyValue(testString);
        // Verifica se o valor retornado é o mesmo
        if (sj.setEmptyValue("test") != sj) {
            throw new AssertionError("setEmptyValue should return the StringJoiner instance");
        }
        // Como não há elementos adicionados, toString deve retornar o emptyValue
        String result = sj.toString();
        if (!result.equals(testString)) {
            throw new AssertionError("Expected toString() to return the emptyValue, but got: " + result);
        }
    }
}
