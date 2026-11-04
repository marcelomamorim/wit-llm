/*
 * @test
 * @summary Test java.util.regex.Matcher.replaceFirst(Function<MatchResult,String>) behavior
 * @run main MatcherWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testReplaceFirstNoMatch();
        testReplaceFirstBasic();
        testReplaceFirstConcurrentModification();
    }

    static void testReplaceFirstNoMatch() {
        Pattern p = Pattern.compile("abc");
        Matcher m = p.matcher("defghijkl");
        String result = m.replaceFirst(new Function<MatchResult, String>() {
            public String apply(MatchResult mr) {
                return "X";
            }
        });
        if (!result.equals("defghijkl")) {
            throw new AssertionError("Expected original string when no match, got: " + result);
        }
    }

    static void testReplaceFirstBasic() {
        Pattern p = Pattern.compile("a(b)c");
        Matcher m = p.matcher("xxabcxxabcxx");
        String result = m.replaceFirst(new Function<MatchResult, String>() {
            public String apply(MatchResult mr) {
                // Replace with group 1 uppercased
                return mr.group(1).toUpperCase();
            }
        });
        // The first "abc" replaced by "B"
        if (!result.equals("xxBxxabcxx")) {
            throw new AssertionError("Unexpected replacement result: " + result);
        }
    }

    static void testReplaceFirstConcurrentModification() {
        Pattern p = Pattern.compile("a");
        final Matcher m = p.matcher("aaa");
        // We create a replacer that modifies the matcher state to trigger ConcurrentModificationException
        Function<MatchResult, String> replacer = new Function<MatchResult, String>() {
            public String apply(MatchResult mr) {
                // Modify matcher state by calling find() again, which increments modCount
                m.find();
                return "X";
            }
        };
        try {
            m.replaceFirst(replacer);
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }
}
