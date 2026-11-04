/*
 * @test
 * @summary Verifies Matcher.region(int,int) argument validation, return value, region bounds and reset behavior
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    interface Action {
        void run();
    }

    static void expectIndexOutOfBounds(String label, Action action) {
        try {
            action.run();
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("Expected IndexOutOfBoundsException: " + label);
    }

    public static void main(String[] args) {
        String text = "abcdef";

        // Returns this and sets the region bounds
        Matcher m = Pattern.compile("b").matcher(text);
        if (m.region(1, 4) != m) {
            throw new AssertionError("region must return this matcher");
        }
        if (m.regionStart() != 1 || m.regionEnd() != 4) {
            throw new AssertionError("unexpected region bounds: "
                    + m.regionStart() + "," + m.regionEnd());
        }

        // Empty region at the end of the input is valid
        Matcher empty = Pattern.compile("b").matcher(text);
        empty.region(text.length(), text.length());
        if (empty.regionStart() != text.length() || empty.regionEnd() != text.length()) {
            throw new AssertionError("empty region at end not accepted");
        }

        // Full-range region is valid
        Matcher full = Pattern.compile("a").matcher(text);
        full.region(0, text.length());
        if (full.regionStart() != 0 || full.regionEnd() != text.length()) {
            throw new AssertionError("full region not accepted");
        }

        // Region resets matching: find must search only inside the region
        Matcher f = Pattern.compile("b").matcher("abcb");
        f.region(2, 4);
        if (!f.find()) {
            throw new AssertionError("expected match inside region");
        }
        if (f.start() != 3) {
            throw new AssertionError("expected match start 3, got " + f.start());
        }

        // Region excluding the first 'b' must not find it
        Matcher g = Pattern.compile("b").matcher("abcd");
        g.region(2, 4);
        if (g.find()) {
            throw new AssertionError("match found outside region");
        }

        // Invalid start (negative)
        final Matcher n1 = Pattern.compile("b").matcher(text);
        expectIndexOutOfBounds("negative start", () -> n1.region(-1, 3));

        // Invalid start (greater than text length)
        final Matcher n2 = Pattern.compile("b").matcher(text);
        expectIndexOutOfBounds("start beyond length", () -> n2.region(text.length() + 1, text.length() + 1));

        // Invalid end (negative)
        final Matcher n3 = Pattern.compile("b").matcher(text);
        expectIndexOutOfBounds("negative end", () -> n3.region(0, -1));

        // Invalid end (greater than text length)
        final Matcher n4 = Pattern.compile("b").matcher(text);
        expectIndexOutOfBounds("end beyond length", () -> n4.region(0, text.length() + 1));

        // Invalid: start greater than end
        final Matcher n5 = Pattern.compile("b").matcher(text);
        expectIndexOutOfBounds("start greater than end", () -> n5.region(3, 2));
    }
}
