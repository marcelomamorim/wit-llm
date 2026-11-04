/*
 * @test
 * @summary Verifies that Matcher.group() returns the entire text of the most recent successful match
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupAfterMatchesReturnsWholeInput();
        testGroupAfterFindReturnsFullMatchNotCapturingGroup();
        testGroupEqualsGroupZero();
        testGroupAfterSuccessiveFinds();
        testGroupRespectsRegion();
    }

    static void testGroupAfterMatchesReturnsWholeInput() {
        Matcher m = Pattern.compile("\\d+").matcher("12345");
        check(m.matches(), "matches() should succeed on digits");
        check("12345".equals(m.group()), "group() after matches() should be the whole input");
    }

    static void testGroupAfterFindReturnsFullMatchNotCapturingGroup() {
        Matcher m = Pattern.compile("(a)(b)?").matcher("xaby");
        check(m.find(), "find() should locate 'ab'");
        check("ab".equals(m.group()), "group() should return full match 'ab'");
        check("a".equals(m.group(1)), "group(1) should return 'a'");
        check("b".equals(m.group(2)), "group(2) should return 'b'");
    }

    static void testGroupEqualsGroupZero() {
        Matcher m = Pattern.compile("h(e)llo").matcher("say hello");
        check(m.find(), "find() should locate 'hello'");
        check(m.group().equals(m.group(0)), "group() should equal group(0)");
        check("hello".equals(m.group()), "group() should be 'hello'");
    }

    static void testGroupAfterSuccessiveFinds() {
        Matcher m = Pattern.compile("cat").matcher("cat dog cat");
        check(m.find(), "first find() should succeed");
        check("cat".equals(m.group()), "first group() should be 'cat'");
        check(m.find(), "second find() should succeed");
        check("cat".equals(m.group()), "second group() should be 'cat'");
        check(!m.find(), "third find() should fail");
    }

    static void testGroupRespectsRegion() {
        Matcher m = Pattern.compile("abc").matcher("xxabcxx");
        m.region(2, 5);
        check(m.find(), "find() within region should succeed");
        check("abc".equals(m.group()), "group() should return 'abc' inside region");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
