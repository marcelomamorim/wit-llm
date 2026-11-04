/*
 * @test
 * @summary Verifies StringUTF16 LinesSpliterator.tryAdvance behavior through the public String.lines() API
 * @run main StringUTF16WitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testNullActionThrowsNPEAfterExhaustion();
        testAdvancesThroughLines();
    }

    static void testNullActionThrowsNPE() {
        String s = "\u4e00\n\u4e8c";
        Spliterator<String> sp = s.lines().spliterator();
        try {
            sp.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action");
        } catch (NullPointerException e) {
            if (!"tryAdvance action missing".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NPE message: " + e.getMessage(), e);
            }
        }
    }

    static void testNullActionThrowsNPEAfterExhaustion() {
        String s = "\u4e00";
        Spliterator<String> sp = s.lines().spliterator();
        List<String> list = new ArrayList<>();
        if (!sp.tryAdvance(x -> list.add(x))) {
            throw new RuntimeException("Expected first line to be available");
        }
        if (sp.tryAdvance(x -> list.add(x))) {
            throw new RuntimeException("Expected no more lines");
        }
        try {
            sp.tryAdvance(null);
            throw new RuntimeException("Expected NullPointerException for null action after exhaustion");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testAdvancesThroughLines() {
        String s = "\u4e00\n\u4e8c";
        Spliterator<String> sp = s.lines().spliterator();
        List<String> list = new ArrayList<>();
        if (!sp.tryAdvance(x -> list.add(x))) {
            throw new RuntimeException("Expected first advance to return true");
        }
        if (!sp.tryAdvance(x -> list.add(x))) {
            throw new RuntimeException("Expected second advance to return true");
        }
        if (sp.tryAdvance(x -> list.add(x))) {
            throw new RuntimeException("Expected third advance to return false");
        }
        if (list.size() != 2 || !"\u4e00".equals(list.get(0)) || !"\u4e8c".equals(list.get(1))) {
            throw new RuntimeException("Unexpected lines: " + list);
        }
    }
}
