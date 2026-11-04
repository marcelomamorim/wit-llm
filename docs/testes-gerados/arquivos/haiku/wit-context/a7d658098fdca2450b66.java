/*
 * @test
 * @summary Verifies that Matcher.start() throws IllegalStateException when no match is available
 * @run main MatcherWitupTest
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        Matcher noMatch = Pattern.compile("a").matcher("b");
        expectIllegalState(noMatch);
        if (noMatch.find()) {
            throw new RuntimeException("unexpected match in input without 'a'");
        }
        expectIllegalState(noMatch);

        Matcher match = Pattern.compile("a").matcher("ba");
        if (!match.find()) {
            throw new RuntimeException("expected a match");
        }
        if (match.start() != 1) {
            throw new RuntimeException("unexpected start: " + match.start());
        }
    }

    static void expectIllegalState(Matcher m) {
        try {
            m.start();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException from start()");
    }
}
