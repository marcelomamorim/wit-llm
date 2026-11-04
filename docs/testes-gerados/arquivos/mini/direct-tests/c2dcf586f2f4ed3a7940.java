/*
 * @test
 * @summary Test StringUTF16.tryAdvance behavior
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;
import java.util.function.IntConsumer;

public class StringUTF16WitupTest {

    static class TestSpliterator extends StringUTF16 {
        int index;
        int fence;
        byte[] array;

        TestSpliterator(byte[] array, int start, int fence) {
            this.array = array;
            this.index = start;
            this.fence = fence;
        }

        // expose tryAdvance for testing
        public boolean tryAdvance(IntConsumer action) {
            if (action == null)
                throw new NullPointerException();
            if (index >= 0 && index < fence) {
                index = advance(array, index, fence, action);
                return true;
            }
            return false;
        }

        // copy of advance method from StringUTF16 (must be present in source for test to compile)
        static int advance(byte[] array, int index, int fence, IntConsumer action) {
            int c = StringUTF16.codePointAt(array, index, fence);
            action.accept(c);
            return index + (Character.isBmpCodePoint(c) ? 1 : 2);
        }
    }

    public static void main(String[] args) {
        testNullAction();
        testTryAdvanceReturnsTrueAndAdvances();
        testTryAdvanceReturnsFalseWhenIndexOutOfRange();
    }

    private static void testNullAction() {
        TestSpliterator spliterator = new TestSpliterator(new byte[2], 0, 1);
        try {
            spliterator.tryAdvance(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    private static void testTryAdvanceReturnsTrueAndAdvances() {
        // Prepare a byte array representing a BMP code point (e.g. 'A' = 0x0041)
        byte[] array = new byte[2];
        StringUTF16.putChar(array, 0, 'A');
        TestSpliterator spliterator = new TestSpliterator(array, 0, 1);

        final int[] consumed = new int[1];
        boolean result = spliterator.tryAdvance(new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        });

        if (!result) {
            throw new Error("tryAdvance should return true when index < fence");
        }
        if (consumed[0] != 'A') {
            throw new Error("tryAdvance action did not receive expected code point");
        }
        if (spliterator.index != 1) {
            throw new Error("tryAdvance did not advance index correctly");
        }
    }

    private static void testTryAdvanceReturnsFalseWhenIndexOutOfRange() {
        byte[] array = new byte[2];
        TestSpliterator spliterator = new TestSpliterator(array, 1, 1); // index == fence

        boolean result = spliterator.tryAdvance(new IntConsumer() {
            public void accept(int value) {
                throw new Error("Action should not be called when index >= fence");
            }
        });

        if (result) {
            throw new Error("tryAdvance should return false when index >= fence");
        }
    }
}
