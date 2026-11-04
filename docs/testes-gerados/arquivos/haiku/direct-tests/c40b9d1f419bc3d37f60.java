/*
 * @test
 * @summary Verifies forEachRemaining behavior of the line spliterator obtained through String.lines() for Latin1 and UTF16 strings
 * @run main StringUTF16WitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testUtf16Lines();
        testLatin1Lines();
        testEmptyString();
        testPartialConsumptionThenForEachRemaining();
        testNullActionThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static List<String> collectAll(String s) {
        List<String> out = new ArrayList<>();
        Spliterator<String> sp = s.lines().spliterator();
        sp.forEachRemaining(out::add);
        return out;
    }

    static void testUtf16Lines() {
        List<String> got = collectAll("\u3042\nb\n");
        check(got.size() == 2, "UTF16 expected 2 lines but got " + got.size());
        check(got.get(0).equals("\u3042"), "UTF16 first line mismatch: " + got.get(0));
        check(got.get(1).equals("b"), "UTF16 second line mismatch: " + got.get(1));
    }

    static void testLatin1Lines() {
        List<String> got = collectAll("a\nbc\n");
        check(got.size() == 2, "Latin1 expected 2 lines but got " + got.size());
        check(got.get(0).equals("a"), "Latin1 first line mismatch: " + got.get(0));
        check(got.get(1).equals("bc"), "Latin1 second line mismatch: " + got.get(1));
    }

    static void testEmptyString() {
        List<String> got = collectAll("");
        check(got.isEmpty(), "Empty string should produce no lines but got " + got.size());
    }

    static void testPartialConsumptionThenForEachRemaining() {
        List<String> out = new ArrayList<>();
        Spliterator<String> sp = "x\ny\nz".lines().spliterator();
        sp.tryAdvance(out::add);
        sp.forEachRemaining(out::add);
        check(out.size() == 3, "Expected 3 lines total but got " + out.size());
        check(out.get(0).equals("x"), "Partial: first mismatch");
        check(out.get(1).equals("y"), "Partial: second mismatch");
        check(out.get(2).equals("z"), "Partial: third mismatch");
    }

    static void testNullActionThrowsNPE() {
        Spliterator<String> sp = "a\nb".lines().spliterator();
        boolean thrown = false;
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "forEachRemaining(null) should throw NullPointerException");
    }
}
