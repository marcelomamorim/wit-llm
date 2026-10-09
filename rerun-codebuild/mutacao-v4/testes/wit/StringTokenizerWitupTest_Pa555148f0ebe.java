/* @test
   @summary Testa o método nextToken(String delim) da classe java.util.StringTokenizer.
   @run main StringTokenizerWitupTest_Pa555148f0ebe
 */
import java.util.StringTokenizer;
public class StringTokenizerWitupTest_Pa555148f0ebe {
    public static void main(String[] args) {
        testNextTokenWithNullDelimiterAfterEnd();
        testNextTokenWithValidDelimiter();
        testNextTokenWithNullDelimiterBeforeEnd();
    }

    private static void testNextTokenWithNullDelimiterAfterEnd() {
        String str = "abc def";
        StringTokenizer tokenizer = new StringTokenizer(str);
        // Consome todos tokens
        while (tokenizer.hasMoreTokens()) {
            tokenizer.nextToken();
        }
        // Agora tenta obter próximo token com delim nulo após o fim
        try {
            tokenizer.nextToken(null);
            System.out.println("PASS: nextToken(null) após fim não lança");
        } catch (Exception e) {
            System.out.println("FAIL: nextToken(null) após fim lançou " + e);
        }
    }

    private static void testNextTokenWithValidDelimiter() {
        String str = "a,b,c";
        StringTokenizer tokenizer = new StringTokenizer(str);
        String token1 = tokenizer.nextToken(",");
        if ("a".equals(token1)) {
            System.out.println("PASS: primeiro token com ',' é 'a'");
        } else {
            System.out.println("FAIL: primeiro token com ',' é '" + token1 + "'");
        }
        String token2 = tokenizer.nextToken(",");
        if ("b".equals(token2)) {
            System.out.println("PASS: segundo token com ',' é 'b'");
        } else {
            System.out.println("FAIL: segundo token com ',' é '" + token2 + "'");
        }
    }

    private static void testNextTokenWithNullDelimiterBeforeEnd() {
        String str = "abc def";
        StringTokenizer tokenizer = new StringTokenizer(str);
        // Consome um token
        tokenizer.nextToken();
        // Tenta obter próximo com null
        try {
            String t = tokenizer.nextToken(null);
            System.out.println("PASS: nextToken(null) antes do fim retornou: '" + t + "'");
        } catch (Exception e) {
            System.out.println("FAIL: nextToken(null) antes do fim lançou " + e);
        }
    }
}
