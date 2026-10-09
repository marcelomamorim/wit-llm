/*
 * @test
 * @summary Test java.util.regex.Matcher.start(String) method for expected exceptions and normal behavior
 * @run main MatcherWitupTest_Pb25d230ac4fa
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherWitupTest_Pb25d230ac4fa {
    public static void main(String[] args) {
        testStartWithNullName();
        testStartWithInvalidName();
        testStartWithValidName();
    }

    private static void testStartWithNullName() {
        Pattern p = Pattern.compile("(?<g>abc)");
        Matcher m = p.matcher("abc");
        try {
            m.start(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testStartWithInvalidName() {
        Pattern p = Pattern.compile("(?<g>abc)");
        Matcher m = p.matcher("abc");
        try {
            m.start("invalidGroupName");
            throw new RuntimeException("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testStartWithValidName() {
        Pattern p = Pattern.compile("(?<g>abc)");
        Matcher m = p.matcher("abc");
        if (!m.find()) {
            throw new RuntimeException("Setup failure: pattern did not match input");
        }
        int start = m.start("g");
        if (start != 0) {
            throw new RuntimeException("Expected start index 0 but got " + start);
        }
    }
}
