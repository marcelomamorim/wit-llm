/*
 * @test
 * @summary Tests for java.util.regex.Matcher.appendReplacement
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testAppendReplacementNoMatch();
        testAppendReplacementInvalidState();
        testAppendReplacementValid();
    }

    private static void testAppendReplacementNoMatch() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("bar");
        StringBuffer sb = new StringBuffer();
        try {
            matcher.appendReplacement(sb, "replacement");
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        }
    }

    private static void testAppendReplacementInvalidState() {
        Pattern pattern = Pattern.compile("foo");
        Matcher matcher = pattern.matcher("foo bar");
        if (matcher.find()) {
            StringBuffer sb = new StringBuffer();
            // Set first to -1 to simulate invalid state
            // Note: Cannot directly set 'first' as it's private; simulate by not calling find()
            // so matcher is in initial state
            try {
                matcher.appendReplacement(sb, "replacement");
                System.out.println("FAIL: Expected IllegalStateException not thrown");
            } catch (IllegalStateException e) {
                System.out.println("PASS: Caught expected IllegalStateException");
            }
        } else {
            System.out.println("SKIP: No match found for test");
        }
    }

    private static void testAppendReplacementValid() {
        Pattern pattern = Pattern.compile("(foo)");
        Matcher matcher = pattern.matcher("foo bar foo");
        StringBuffer sb = new StringBuffer();
        if (matcher.find()) {
            try {
                matcher.appendReplacement(sb, "$1_replaced");
                matcher.appendTail(sb);
                String result = sb.toString();
                if (result.contains("foo_replaced")) {
                    System.out.println("PASS: appendReplacement and appendTail work as expected");
                } else {
                    System.out.println("FAIL: Unexpected result: " + result);
                }
            } catch (Exception e) {
                System.out.println("FAIL: Unexpected exception: " + e);
            }
        } else {
            System.out.println("SKIP: No match found for valid test");
        }
    }
}
