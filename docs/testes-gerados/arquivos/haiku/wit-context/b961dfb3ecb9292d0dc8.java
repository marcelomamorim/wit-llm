/*
 * @test
 * @summary Verify Matcher.start() throws IllegalStateException when no match is available
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        Matcher noFindYet = Pattern.compile("a").matcher("bbb");
        expectIllegalState(noFindYet);

        Matcher failedFind = Pattern.compile("a").matcher("bbb");
        if (failedFind.find()) {
            throw new RuntimeException("unexpected match");
        }
        expectIllegalState(failedFind);

        Matcher matchAtTwo = Pattern.compile("a").matcher("xxa");
        if (!matchAtTwo.find()) {
            throw new RuntimeException("expected match");
        }
        if (matchAtTwo.start() != 2) {
            throw new RuntimeException("unexpected start: " + matchAtTwo.start());
        }

        Matcher matchAtZero = Pattern.compile("a").matcher("abc");
        if (!matchAtZero.find()) {
            throw new RuntimeException("expected match");
        }
        if (matchAtZero.start() != 0) {
            throw new RuntimeException("unexpected start: " + matchAtZero.start());
        }
    }

    private static void expectIllegalState(Matcher m) {
        try {
            m.start();
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("expected IllegalStateException");
    }
}
