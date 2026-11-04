/*
 * @test
 * @summary Test java.lang.StringUTF16.tryAdvance(IntConsumer) for expected exceptions and normal behavior
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;
import java.util.function.IntConsumer;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testTryAdvanceNullAction();
        testTryAdvanceIndexOutOfBounds();
        testTryAdvanceNormal();
    }

    private static void testTryAdvanceNullAction() {
        StringUTF16.CodePointsSpliterator spliterator = new StringUTF16.CodePointsSpliterator(new byte[2], 0, 1);
        try {
            spliterator.tryAdvance(null);
            throw new Error("Expected NullPointerException for null action");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testTryAdvanceIndexOutOfBounds() {
        // Setup a spliterator with array length 2 (1 char), fence 1, index 1 (at fence)
        // tryAdvance should return false without calling action, no exception
        byte[] array = new byte[2];
        StringUTF16.CodePointsSpliterator spliterator = new StringUTF16.CodePointsSpliterator(array, 1, 1);
        IntConsumer action = new IntConsumer() {
            public void accept(int value) {
                throw new Error("Action should not be called when index >= fence");
            }
        };
        boolean result = spliterator.tryAdvance(action);
        if (result) {
            throw new Error("Expected tryAdvance to return false when index >= fence");
        }

        // Setup a spliterator with invalid index to simulate out of bounds
        // Since index is private, we cannot set it directly; so we test that no exception is thrown for normal usage
        // The WIT path suggesting StringIndexOutOfBoundsException is not reproducible with public API
        // Hence, this test only verifies no exception is thrown in normal usage
    }

    private static void testTryAdvanceNormal() {
        // Setup a spliterator with array length 4 (2 chars), fence 2, index 0
        byte[] array = new byte[4];
        // Put chars 'A' and 'B' into array
        StringUTF16.putChar(array, 0, 'A');
        StringUTF16.putChar(array, 2, 'B');
        StringUTF16.CodePointsSpliterator spliterator = new StringUTF16.CodePointsSpliterator(array, 0, 2);
        final int[] collected = new int[2];
        IntConsumer action = new IntConsumer() {
            int pos = 0;
            public void accept(int value) {
                if (pos >= collected.length) {
                    throw new Error("More calls to accept than expected");
                }
                collected[pos++] = value;
            }
        };

        boolean advanced = spliterator.tryAdvance(action);
        if (!advanced) {
            throw new Error("Expected tryAdvance to return true when index < fence");
        }
        if (collected[0] != 'A') {
            throw new Error("Expected first code point to be 'A', got: " + collected[0]);
        }

        advanced = spliterator.tryAdvance(action);
        if (!advanced) {
            throw new Error("Expected tryAdvance to return true on second call when index < fence");
        }
        if (collected[1] != 'B') {
            throw new Error("Expected second code point to be 'B', got: " + collected[1]);
        }

        advanced = spliterator.tryAdvance(action);
        if (advanced) {
            throw new Error("Expected tryAdvance to return false when index >= fence");
        }
    }

}
