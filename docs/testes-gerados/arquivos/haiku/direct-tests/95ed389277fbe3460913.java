/*
 * @test
 * @summary Observable behavior of CharBuffer character streams, which traverse elements through CharBufferSpliterator
 * @run main CharBufferSpliteratorWitupTest
 */

import java.nio.CharBuffer;
import java.util.Arrays;

public class CharBufferSpliteratorWitupTest {

    public static void main(String[] args) {
        testFullBufferTraversal();
        testPositionAndLimitRespected();
        testEmptyBuffer();
        testForEachAccumulatesAllElements();
    }

    static void testFullBufferTraversal() {
        CharBuffer cb = CharBuffer.wrap("abc");
        int[] values = cb.chars().toArray();
        check(Arrays.equals(values, new int[] {'a', 'b', 'c'}),
              "full buffer traversal expected [a, b, c] but was " + Arrays.toString(values));
    }

    static void testPositionAndLimitRespected() {
        // wrap(csq, start, end) sets position=start and limit=end
        CharBuffer cb = CharBuffer.wrap("abcd", 1, 3);
        int[] values = cb.chars().toArray();
        check(Arrays.equals(values, new int[] {'b', 'c'}),
              "slice traversal expected [b, c] but was " + Arrays.toString(values));
    }

    static void testEmptyBuffer() {
        CharBuffer cb = CharBuffer.wrap("");
        long count = cb.chars().count();
        check(count == 0L, "empty buffer expected count 0 but was " + count);
    }

    static void testForEachAccumulatesAllElements() {
        CharBuffer cb = CharBuffer.wrap("xyz", 0, 3);
        StringBuilder sb = new StringBuilder();
        cb.chars().forEach(c -> sb.appendCodePoint(c));
        check(sb.toString().equals("xyz"),
              "forEach expected xyz but was " + sb);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
