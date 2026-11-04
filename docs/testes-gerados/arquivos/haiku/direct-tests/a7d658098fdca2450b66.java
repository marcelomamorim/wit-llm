/*
 * @test
 * @summary Checks the observable results of Matcher.start() after successful match operations
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {
    public static void main(String[] args) {
        testFirstFindStart();
        testSecondFindStart();
        testMatchesStart();
        testRegionStart();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testFirstFindStart() {
        Matcher m = Pattern.compile("b+").matcher("aabbbc");
        check(m.find(), "find() should succeed");
        check(m.start() == 2, "expected start 2 but got " + m.start());
    }

    static void testSecondFindStart() {
        Matcher m = Pattern.compile("ab").matcher("ab ab");
        check(m.find(), "first find() should succeed");
        check(m.start() == 0, "expected first start 0 but got " + m.start());
        check(m.find(), "second find() should succeed");
        check(m.start() == 3, "expected second start 3 but got " + m.start());
    }

    static void testMatchesStart() {
        Matcher m = Pattern.compile("abc").matcher("abc");
        check(m.matches(), "matches() should succeed");
        check(m.start() == 0, "expected start 0 but got " + m.start());
    }

    static void testRegionStart() {
        Matcher m = Pattern.compile("x").matcher("axbx");
        m.region(1, 4);
        check(m.find(), "first find() in region should succeed");
        check(m.start() == 1, "expected absolute start 1 but got " + m.start());
        check(m.find(), "second find() in region should succeed");
        check(m.start() == 3, "expected absolute start 3 but got " + m.start());
    }
}
