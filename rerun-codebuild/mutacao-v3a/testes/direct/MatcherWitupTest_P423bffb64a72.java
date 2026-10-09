/* @test    @summary Test for Matcher.appendReplacement method handling no match scenario and normal replacement.    @run main MatcherWitupTest_P423bffb64a72 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest_P423bffb64a72 {
    public static void main(String[] args) {
        testAppendReplacementWithoutMatch();
        testAppendReplacementWithMatch();
    }

    static void testAppendReplacementWithoutMatch() {
        Pattern pattern = Pattern.compile("abc");
        Matcher matcher = pattern.matcher("def");
        StringBuilder sb = new StringBuilder();
        boolean exceptionThrown = false;
        try {
            matcher.appendReplacement(sb, "xyz");
        } catch (IllegalStateException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalStateException when no match is available");
        }
    }

    static void testAppendReplacementWithMatch() {
        Pattern pattern = Pattern.compile("(\d+)");
        Matcher matcher = pattern.matcher("Number: 42");
        if (matcher.find()) {
            StringBuilder sb = new StringBuilder();
            matcher.appendReplacement(sb, "Number: $1") ;
            String result = sb.toString();
            if (!result.equals("Number: $1")) {
                throw new AssertionError("Unexpected replacement result: " + result);
            }
        } else {
            throw new AssertionError("Matcher did not find expected match");
        }
    }
}
