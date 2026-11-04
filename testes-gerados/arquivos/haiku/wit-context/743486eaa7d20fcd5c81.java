/*
 * @test
 * @summary Verify forEachRemaining of the Latin1 lines spliterator via public String.lines() API
 * @run main StringLatin1WitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testNullActionThrowsNPE();
        testCollectsAllLines();
    }

    static void testNullActionThrowsNPE() {
        Spliterator<String> sp = "a\nb\nc".lines().spliterator();
        try {
            sp.forEachRemaining(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null action");
    }

    static void testCollectsAllLines() {
        Spliterator<String> sp = "a\nb\nc".lines().spliterator();
        List<String> out = new ArrayList<>();
        Consumer<String> collector = out::add;
        sp.forEachRemaining(collector);
        List<String> expected = Arrays.asList("a", "b", "c");
        if (!out.equals(expected)) {
            throw new RuntimeException("Unexpected lines: " + out);
        }
    }
}
