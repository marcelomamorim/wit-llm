/* @test
   @summary Testa o método appendReplacement do Matcher para comportamentos observáveis.
   @run main MatcherWitupTest_P69c977afd068
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Objects;

public class MatcherWitupTest_P69c977afd068 {
    public static void main(String[] args) {
        testAppendReplacementNoMatch();
        testAppendReplacementInvalidState();
    }

    private static void testAppendReplacementNoMatch() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("def");
        StringBuilder sb = new StringBuilder();
        try {
            matcher.appendReplacement(sb, "replacement");
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            // expected
        }
    }

    private static void testAppendReplacementInvalidState() {
        Pattern pattern = Pattern.compile("a");
        Matcher matcher = pattern.matcher("a");
        StringBuilder sb = new StringBuilder();
        if (matcher.find()) {
            // Simulate invalid state by not calling find() before appendReplacement
            Matcher m = matcher;
            m.reset();
            try {
                m.appendReplacement(sb, "b");
                throw new AssertionError("Expected IllegalStateException not thrown");
            } catch (IllegalStateException e) {
                // expected
            }
        }
    }
}
