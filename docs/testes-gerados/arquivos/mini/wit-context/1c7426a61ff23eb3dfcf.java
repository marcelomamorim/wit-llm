/*
 * @test
 * @summary Test java.util.regex.Matcher.replaceAll(Function<MatchResult,String>) behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.function.Function;
import java.util.ConcurrentModificationException;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testReplaceAllNullReplacer();
        testReplaceAllNoMatchReturnsOriginal();
        testReplaceAllSimpleReplacement();
        testReplaceAllConcurrentModificationException();
    }

    private static void testReplaceAllNullReplacer() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abc");
        try {
            m.replaceAll(null);
            throw new Error("Expected NullPointerException for null replacer");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testReplaceAllNoMatchReturnsOriginal() {
        Pattern p = Pattern.compile("z");
        Matcher m = p.matcher("abc");
        String result = m.replaceAll(new Function<Matcher, String>() {
            public String apply(Matcher mr) {
                return "X";
            }
        });
        if (!"abc".equals(result)) {
            throw new Error("Expected original string when no match found, got: " + result);
        }
    }

    private static void testReplaceAllSimpleReplacement() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abcabc");
        String result = m.replaceAll(new Function<Matcher, String>() {
            public String apply(Matcher mr) {
                return "x";
            }
        });
        if (!"xbcxbc".equals(result)) {
            throw new Error("Expected all 'a' replaced by 'x', got: " + result);
        }
    }

    private static void testReplaceAllConcurrentModificationException() {
        Pattern p = Pattern.compile("a");
        final Matcher m = p.matcher("abcabc");
        try {
            m.replaceAll(new Function<Matcher, String>() {
                private int count = 0;
                public String apply(Matcher mr) {
                    count++;
                    if (count == 2) {
                        // Modify matcher state to trigger ConcurrentModificationException
                        m.reset();
                    }
                    return "x";
                }
            });
            throw new Error("Expected ConcurrentModificationException due to modification during replaceAll");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
