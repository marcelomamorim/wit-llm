/*
 * @test
 * @summary Tests observable behavior of Matcher.group(int): IllegalStateException without a match, IndexOutOfBoundsException for invalid group indices, and null for non-participating groups.
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testGroupBeforeAnyMatchThrowsIllegalState();
        testGroupAfterFailedFindThrowsIllegalState();
        testNegativeGroupThrowsIndexOutOfBounds();
        testGroupAboveGroupCountThrowsIndexOutOfBounds();
        testGroupZeroAndCapturingGroupsAfterFind();
        testNonParticipatingGroupReturnsNull();
    }

    static void testGroupBeforeAnyMatchThrowsIllegalState() {
        Matcher m = Pattern.compile("a").matcher("a");
        try {
            m.group(0);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException before any match");
    }

    static void testGroupAfterFailedFindThrowsIllegalState() {
        Matcher m = Pattern.compile("ab").matcher("zzz");
        if (m.find()) {
            throw new RuntimeException("find() unexpectedly matched");
        }
        try {
            m.group(0);
        } catch (IllegalStateException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalStateException after failed find");
    }

    static void testNegativeGroupThrowsIndexOutOfBounds() {
        Matcher m = Pattern.compile("(a)").matcher("a");
        if (!m.find()) {
            throw new RuntimeException("Expected a match");
        }
        try {
            m.group(-1);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for group -1");
    }

    static void testGroupAboveGroupCountThrowsIndexOutOfBounds() {
        Matcher m = Pattern.compile("(a)(b)").matcher("ab");
        if (!m.find()) {
            throw new RuntimeException("Expected a match");
        }
        if (m.groupCount() != 2) {
            throw new RuntimeException("Unexpected groupCount: " + m.groupCount());
        }
        try {
            m.group(3);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected IndexOutOfBoundsException for group 3");
    }

    static void testGroupZeroAndCapturingGroupsAfterFind() {
        Matcher m = Pattern.compile("(a)(b)").matcher("xaby");
        if (!m.find()) {
            throw new RuntimeException("Expected a match");
        }
        if (!"ab".equals(m.group(0))) {
            throw new RuntimeException("group(0) mismatch: " + m.group(0));
        }
        if (!"a".equals(m.group(1))) {
            throw new RuntimeException("group(1) mismatch: " + m.group(1));
        }
        if (!"b".equals(m.group(2))) {
            throw new RuntimeException("group(2) mismatch: " + m.group(2));
        }
    }

    static void testNonParticipatingGroupReturnsNull() {
        Matcher m = Pattern.compile("(a)|(b)").matcher("b");
        if (!m.find()) {
            throw new RuntimeException("Expected a match");
        }
        if (m.group(1) != null) {
            throw new RuntimeException("Expected null for non-participating group 1");
        }
        if (!"b".equals(m.group(2))) {
            throw new RuntimeException("group(2) mismatch: " + m.group(2));
        }
    }
}
