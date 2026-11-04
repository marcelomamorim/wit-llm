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
        testReplaceAllWithSimpleReplacer();
        testReplaceAllNoMatchReturnsOriginal();
        testReplaceAllConcurrentModificationException();
    }

    static void testReplaceAllWithSimpleReplacer() {
        Pattern p = Pattern.compile("a");
        Matcher m = p.matcher("abracadabra");
        String result = m.replaceAll(new Function<Matcher, String>() {
            public String apply(Matcher mr) {
                return "x";
            }
        });
        if (!"xbrxcxdxbrx".equals(result)) {
            throw new AssertionError("Expected xbrxcxdxbrx but got: " + result);
        }
    }

    static void testReplaceAllNoMatchReturnsOriginal() {
        Pattern p = Pattern.compile("z");
        Matcher m = p.matcher("abracadabra");
        String result = m.replaceAll(new Function<Matcher, String>() {
            public String apply(Matcher mr) {
                return "x";
            }
        });
        if (!"abracadabra".equals(result)) {
            throw new AssertionError("Expected original string when no match but got: " + result);
        }
    }

    static void testReplaceAllConcurrentModificationException() {
        Pattern p = Pattern.compile("a");
        final Matcher m = p.matcher("abracadabra");
        try {
            m.replaceAll(new Function<Matcher, String>() {
                public String apply(Matcher mr) {
                    // Modify matcher state to trigger ConcurrentModificationException
                    m.reset();
                    return "x";
                }
            });
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // Expected
        }
    }
}
