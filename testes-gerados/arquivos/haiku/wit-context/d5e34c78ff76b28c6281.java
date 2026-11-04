/*
 * @test
 * @summary Witup-derived checks for StringLatin1 LinesSpliterator.tryAdvance reached through public String.lines()
 * @run main StringLatin1WitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testTryAdvanceSequence();
        testEmptyString();
        testTrailingNewline();
        testLatin1Content();
    }

    static void testNullActionThrowsNPE() {
        Spliterator<String> sp = "abc\ndef".lines().spliterator();
        try {
            sp.tryAdvance(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testTryAdvanceSequence() {
        Spliterator<String> sp = "abc\ndef".lines().spliterator();
        List<String> out = new ArrayList<>();
        if (!sp.tryAdvance(out::add)) {
            throw new RuntimeException("first tryAdvance should return true");
        }
        if (!sp.tryAdvance(out::add)) {
            throw new RuntimeException("second tryAdvance should return true");
        }
        if (sp.tryAdvance(out::add)) {
            throw new RuntimeException("third tryAdvance should return false");
        }
        if (!"[abc, def]".equals(out.toString())) {
            throw new RuntimeException("unexpected lines: " + out);
        }
    }

    static void testEmptyString() {
        Spliterator<String> sp = "".lines().spliterator();
        List<String> out = new ArrayList<>();
        if (sp.tryAdvance(out::add)) {
            throw new RuntimeException("empty string should yield no lines");
        }
        if (!out.isEmpty()) {
            throw new RuntimeException("consumer must not be called");
        }
    }

    static void testTrailingNewline() {
        Spliterator<String> sp = "a\n".lines().spliterator();
        List<String> out = new ArrayList<>();
        if (!sp.tryAdvance(out::add)) {
            throw new RuntimeException("expected one line");
        }
        if (sp.tryAdvance(out::add)) {
            throw new RuntimeException("no extra line expected after trailing newline");
        }
        if (!"[a]".equals(out.toString())) {
            throw new RuntimeException("unexpected lines: " + out);
        }
    }

    static void testLatin1Content() {
        Spliterator<String> sp = "\u00e9\n\u00ff".lines().spliterator();
        List<String> out = new ArrayList<>();
        while (sp.tryAdvance(out::add)) {
        }
        if (out.size() != 2) {
            throw new RuntimeException("expected 2 lines, got " + out.size());
        }
        if (!"\u00e9".equals(out.get(0)) || !"\u00ff".equals(out.get(1))) {
            throw new RuntimeException("unexpected Latin1 content: " + out);
        }
    }
}
