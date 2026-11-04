/*
 * @test
 * @summary Test java.util.Spliterators.tryAdvance(IntConsumer) method
 * @run main SpliteratorsWitupTest
 */

import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithNullAction();
        testTryAdvanceWithinBounds();
        testTryAdvanceOutOfBounds();
    }

    private static void testTryAdvanceWithNullAction() {
        final Spliterators spliterator = new Spliterators() {
            int index = 0;
            int fence = 1;
            int[] array = {42};
            public boolean tryAdvance(IntConsumer action) {
                if (action == null)
                    throw new NullPointerException();
                if (index >= 0 && index < fence) {
                    action.accept(array[index++]);
                    return true;
                }
                return false;
            }
        };

        try {
            spliterator.tryAdvance(null);
            throw new Error("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    private static void testTryAdvanceWithinBounds() {
        final int[] values = {10};
        final int[] consumed = new int[1];

        Spliterators spliterator = new Spliterators() {
            int index = 0;
            int fence = 1;
            int[] array = values;
            public boolean tryAdvance(IntConsumer action) {
                if (action == null)
                    throw new NullPointerException();
                if (index >= 0 && index < fence) {
                    action.accept(array[index++]);
                    return true;
                }
                return false;
            }
        };

        boolean result = spliterator.tryAdvance(new IntConsumer() {
            public void accept(int value) {
                consumed[0] = value;
            }
        });

        if (!result) {
            throw new Error("Expected tryAdvance to return true");
        }
        if (consumed[0] != 10) {
            throw new Error("Expected consumed value 10 but got " + consumed[0]);
        }
    }

    private static void testTryAdvanceOutOfBounds() {
        Spliterators spliterator = new Spliterators() {
            int index = 1;
            int fence = 1;
            int[] array = {10};
            public boolean tryAdvance(IntConsumer action) {
                if (action == null)
                    throw new NullPointerException();
                if (index >= 0 && index < fence) {
                    action.accept(array[index++]);
                    return true;
                }
                return false;
            }
        };

        boolean result = spliterator.tryAdvance(new IntConsumer() {
            public void accept(int value) {
                throw new Error("Should not be called");
            }
        });

        if (result) {
            throw new Error("Expected tryAdvance to return false");
        }
    }
}
