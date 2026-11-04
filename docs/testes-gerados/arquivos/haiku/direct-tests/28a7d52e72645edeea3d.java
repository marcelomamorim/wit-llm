/*
 * @test
 * @summary Tests observable behavior of Matcher.replaceFirst(Function)
 * @run main MatcherWitupTest
 */

import java.util.ConcurrentModificationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherWitupTest {

    public static void main(String[] args) {
        testReplacesOnlyFirstMatch();
        testNoMatchReturnsInputText();
        testReplacerUsesMatchResultGroups();
        testReplacementProcessesGroupReferences();
        testNullReplacerThrowsNpe();
        testReplacerModifyingMatcherThrowsCme();
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected [" + expected + "] but got [" + actual + "]");
        }
    }

    static void testReplacesOnlyFirstMatch() {
        Matcher m = Pattern.compile("a+").matcher("baaac aa");
        String result = m.replaceFirst(r -> "X");
        check("bXc aa", result);
    }

    static void testNoMatchReturnsInputText() {
        Matcher m = Pattern.compile("q").matcher("xyz");
        String result = m.replaceFirst(r -> "never");
        check("xyz", result);
    }

    static void testReplacerUsesMatchResultGroups() {
        Matcher m = Pattern.compile("(a+)c").matcher("baaac");
        String result = m.replaceFirst(r -> "<" + r.group(1) + ">");
        check("b<aaa>", result);
    }

    static void testReplacementProcessesGroupReferences() {
        Matcher m = Pattern.compile("(a+)").matcher("baaac");
        String result = m.replaceFirst(r -> "[$1]");
        check("b[aaa]c", result);
    }

    static void testNullReplacerThrowsNpe() {
        Matcher m = Pattern.compile("a").matcher("a");
        try {
            m.replaceFirst(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null replacer");
    }

    static void testReplacerModifyingMatcherThrowsCme() {
        Matcher m = Pattern.compile("a").matcher("aa");
        try {
            m.replaceFirst(r -> {
                m.reset();
                return "x";
            });
        } catch (ConcurrentModificationException e) {
            return;
        }
        throw new RuntimeException("ConcurrentModificationException expected");
    }
}
