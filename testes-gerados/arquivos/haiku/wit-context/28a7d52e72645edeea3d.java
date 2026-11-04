/*
 * @test
 * @summary Tests Matcher.replaceFirst(Function) observable behavior, including null replacer rejection
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullReplacerThrowsNPE() {
        Matcher m = Pattern.compile("a").matcher("banana");
        boolean thrown = false;
        try {
            m.replaceFirst(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null replacer");
    }

    static void testNoMatchReturnsInputText() {
        Matcher m = Pattern.compile("z").matcher("banana");
        String result = m.replaceFirst(r -> "X");
        check("banana".equals(result), "expected unchanged input, got: " + result);
    }

    static void testReplacesFirstOccurrenceOnly() {
        Matcher m = Pattern.compile("a").matcher("banana");
        String result = m.replaceFirst(r -> "X");
        check("bXnana".equals(result), "unexpected result: " + result);
    }

    static void testReplacerReceivesMatchResult() {
        Matcher m = Pattern.compile("(an)").matcher("banana");
        String result = m.replaceFirst(r -> {
            check(r.group(1).equals("an"), "unexpected group: " + r.group(1));
            check(r.start() == 1, "unexpected start: " + r.start());
            return "[" + r.group(1) + "]";
        });
        check("b[an]ana".equals(result), "unexpected result: " + result);
    }

    public static void main(String[] args) {
        testNullReplacerThrowsNPE();
        testNoMatchReturnsInputText();
        testReplacesFirstOccurrenceOnly();
        testReplacerReceivesMatchResult();
    }
}
