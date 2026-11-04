/*
 * @test
 * @summary Verify EnumSet.of(e1, e2, e3, e4) collects the four elements, deduplicates, and rejects null
 * @run main EnumSetWitupTest
 */

import java.util.EnumSet;
import java.util.Iterator;

public class EnumSetWitupTest {

    enum Color { RED, GREEN, BLUE, YELLOW, BLACK }

    public static void main(String[] args) {
        testAllDistinctElementsArePresent();
        testIterationFollowsDeclarationOrder();
        testDuplicateArgumentsAreDeduplicated();
        testResultIsIndependentOfPreviousCalls();
        testNullSecondArgumentThrowsNpe();
        testNullFourthArgumentThrowsNpe();
    }

    static void testAllDistinctElementsArePresent() {
        EnumSet<Color> s = EnumSet.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        check(s.size() == 4, "expected size 4 but was " + s.size());
        check(s.contains(Color.RED), "missing RED");
        check(s.contains(Color.GREEN), "missing GREEN");
        check(s.contains(Color.BLUE), "missing BLUE");
        check(s.contains(Color.YELLOW), "missing YELLOW");
        check(!s.contains(Color.BLACK), "BLACK must not be present");
    }

    static void testIterationFollowsDeclarationOrder() {
        EnumSet<Color> s = EnumSet.of(Color.YELLOW, Color.RED, Color.BLUE, Color.GREEN);
        Iterator<Color> it = s.iterator();
        check(it.next() == Color.RED, "first element should be RED");
        check(it.next() == Color.GREEN, "second element should be GREEN");
        check(it.next() == Color.BLUE, "third element should be BLUE");
        check(it.next() == Color.YELLOW, "fourth element should be YELLOW");
        check(!it.hasNext(), "iterator should be exhausted");
    }

    static void testDuplicateArgumentsAreDeduplicated() {
        EnumSet<Color> s = EnumSet.of(Color.RED, Color.RED, Color.GREEN, Color.GREEN);
        check(s.size() == 2, "expected size 2 but was " + s.size());
        check(s.contains(Color.RED) && s.contains(Color.GREEN), "expected RED and GREEN");
    }

    static void testResultIsIndependentOfPreviousCalls() {
        EnumSet<Color> first = EnumSet.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        first.remove(Color.RED);
        EnumSet<Color> second = EnumSet.of(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        check(second.size() == 4, "new result should have size 4 but was " + second.size());
        check(second.contains(Color.RED), "new result should contain RED");
    }

    static void testNullSecondArgumentThrowsNpe() {
        try {
            EnumSet.of(Color.RED, null, Color.BLUE, Color.YELLOW);
            throw new AssertionError("expected NullPointerException for null e2");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullFourthArgumentThrowsNpe() {
        try {
            EnumSet.of(Color.RED, Color.GREEN, Color.BLUE, null);
            throw new AssertionError("expected NullPointerException for null e4");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
