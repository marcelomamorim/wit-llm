/*
 * @test
 * @summary Tests tryAdvance of the UTF16 lines spliterator via String.lines()
 * @run main StringUTF16WitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testAdvancesLinesInOrder();
        testExhaustedReturnsFalse();
        testNullActionThrowsAndDoesNotAdvance();
    }

    static void testAdvancesLinesInOrder() {
        String s = "\u4e2d\nb\u4e2d";
        Spliterator<String> sp = s.lines().spliterator();
        List<String> out = new ArrayList<>();
        while (sp.tryAdvance(out::add)) {
        }
        if (out.size() != 2) {
            throw new RuntimeException("Expected 2 lines, got " + out.size());
        }
        if (!out.get(0).equals("\u4e2d")) {
            throw new RuntimeException("Unexpected first line: " + out.get(0));
        }
        if (!out.get(1).equals("b\u4e2d")) {
            throw new RuntimeException("Unexpected second line: " + out.get(1));
        }
    }

    static void testExhaustedReturnsFalse() {
        Spliterator<String> sp = "\u4e2d".lines().spliterator();
        List<String> out = new ArrayList<>();
        if (!sp.tryAdvance(out::add)) {
            throw new RuntimeException("First tryAdvance should return true");
        }
        if (sp.tryAdvance(out::add)) {
            throw new RuntimeException("tryAdvance after end should return false");
        }
        if (sp.tryAdvance(out::add)) {
            throw new RuntimeException("Repeated tryAdvance after end should return false");
        }
        if (out.size() != 1 || !out.get(0).equals("\u4e2d")) {
            throw new RuntimeException("Unexpected output: " + out);
        }
    }

    static void testNullActionThrowsAndDoesNotAdvance() {
        Spliterator<String> sp = "\u4e2d\nx".lines().spliterator();
        boolean thrown = false;
        try {
            sp.tryAdvance(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected NullPointerException for null action");
        }
        List<String> out = new ArrayList<>();
        if (!sp.tryAdvance(out::add) || !out.get(0).equals("\u4e2d")) {
            throw new RuntimeException("Spliterator advanced after null action rejection");
        }
    }
}
