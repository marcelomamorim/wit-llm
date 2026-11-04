/*
 * @test
 * @summary Verifies Matcher.end(String) for matched, unmatched, and invalid named groups, and for calls before a match
 * @run main MatcherWitupTest
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testEndForMatchedNamedGroup();
        testEndForUnmatchedNamedGroupIsMinusOne();
        testEndBeforeSuccessfulMatchThrowsIllegalState();
        testNullNameThrowsNullPointerException();
        testUnknownGroupNameThrowsIllegalArgument();
    }

    static void testEndForMatchedNamedGroup() {
        Pattern p = Pattern.compile("(?<x>a+)b");
        Matcher m = p.matcher("xaab");
        if (!m.find()) {
            throw new AssertionError("expected a match");
        }
        int end = m.end("x");
        if (end != 3) {
            throw new AssertionError("expected end(x)=3 but was " + end);
        }
    }

    static void testEndForUnmatchedNamedGroupIsMinusOne() {
        Pattern p = Pattern.compile("(?<x>a)|b");
        Matcher m = p.matcher("b");
        if (!m.find()) {
            throw new AssertionError("expected a match");
        }
        int end = m.end("x");
        if (end != -1) {
            throw new AssertionError("expected end(x)=-1 but was " + end);
        }
    }

    static void testEndBeforeSuccessfulMatchThrowsIllegalState() {
        Pattern p = Pattern.compile("(?<x>a)");
        final Matcher m = p.matcher("zzz");
        if (m.find()) {
            throw new AssertionError("expected no match");
        }
        expectFailure(IllegalStateException.class, () -> m.end("x"));
    }

    static void testNullNameThrowsNullPointerException() {
        Pattern p = Pattern.compile("(?<x>a)");
        final Matcher m = p.matcher("a");
        if (!m.find()) {
            throw new AssertionError("expected a match");
        }
        expectFailure(NullPointerException.class, () -> m.end(null));
    }

    static void testUnknownGroupNameThrowsIllegalArgument() {
        Pattern p = Pattern.compile("(?<x>a)");
        final Matcher m = p.matcher("a");
        if (!m.find()) {
            throw new AssertionError("expected a match");
        }
        expectFailure(IllegalArgumentException.class, () -> m.end("nope"));
    }

    static void expectFailure(Class<? extends RuntimeException> type, Runnable action) {
        try {
            action.run();
        } catch (RuntimeException e) {
            if (type.isInstance(e)) {
                return;
            }
            throw new AssertionError("unexpected exception: " + e, e);
        }
        throw new AssertionError("expected " + type.getName());
    }
}
