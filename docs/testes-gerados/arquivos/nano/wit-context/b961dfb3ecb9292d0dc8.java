/*
 * @test
 * @summary Testa o método start() da classe java.util.regex.Matcher para verificar comportamento esperado e exceções.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testStartWithMatch();
        testStartWithoutMatch();
    }

    private static void testStartWithMatch() {
        Pattern pattern = Pattern.compile("\d+");
        Matcher matcher = pattern.matcher("12345");
        if (matcher.find()) {
            int startIndex = matcher.start();
            if (startIndex != 0) {
                throw new AssertionError("Expected start index 0, got " + startIndex);
            }
        } else {
            throw new AssertionError("Matcher should find a match.");
        }
    }

    private static void testStartWithoutMatch() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("12345");
        if (matcher.find()) {
            // Não faz nada, apenas para garantir que find() foi chamado.
        }
        try {
            matcher.start();
            throw new AssertionError("Expected IllegalStateException when calling start() without a match.");
        } catch (IllegalStateException e) {
            // Esperado
        }
    }
}
