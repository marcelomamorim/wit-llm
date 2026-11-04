/*
 * @test
 * @summary Exercises StringLatin1 spliterator forEachRemaining through String.lines() on Latin1 strings
 * @run main StringLatin1WitupTest
 */

import java.util.ArrayList;
import java.util.List;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        check("a\nb\nc", List.of("a", "b", "c"));
        check("x\n", List.of("x"));
        check("", List.of());
        check("a\r\nb", List.of("a", "b"));
        check("\n\n", List.of("", ""));
        check("\u00e9\n\u00fc", List.of("\u00e9", "\u00fc"));
    }

    static void check(String input, List<String> expected) {
        List<String> actual = new ArrayList<>();
        input.lines().forEach(actual::add);
        if (!expected.equals(actual)) {
            throw new RuntimeException("For input \"" + input + "\" expected "
                    + expected + " but was " + actual);
        }
    }
}
