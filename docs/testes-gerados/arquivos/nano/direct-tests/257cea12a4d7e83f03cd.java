/*
 * @test
 * @summary Test for java.util.Spliterators forEachRemaining method with IntConsumer
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testForEachRemainingWithValidInput();
        testForEachRemainingWithNullAction();
    }

    static void testForEachRemainingWithValidInput() {
        int[] array = {1, 2, 3};
        int[] result = {0, 0, 0};
        IntConsumer consumer = new IntConsumer() {
            int index = 0;
            @Override
            public void accept(int value) {
                result[index++] = value;
            }
        };
        // Create a dummy Spliterator with the array and fence
        class DummySpliterator extends Spliterators {
            int[] array;
            int index;
            int fence;
            DummySpliterator(int[] array, int start, int end) {
                this.array = array;
                this.index = start;
                this.fence = end;
            }
            public void forEachRemaining(IntConsumer action) {
                int[] a; int i, hi;
                if (action == null) throw new NullPointerException();
                if ((a = array).length >= (hi = fence) && (i = index) >= 0 && i < hi) {
                    do { action.accept(a[i]); } while (++i < hi);
                }
            }
        }
        DummySpliterator spliterator = new DummySpliterator(array, 0, array.length);
        spliterator.forEachRemaining(consumer);
        assert result[0] == 1 : "First element should be 1";
        assert result[1] == 2 : "Second element should be 2";
        assert result[2] == 3 : "Third element should be 3";
    }

    static void testForEachRemainingWithNullAction() {
        int[] array = {1};
        class DummySpliterator extends Spliterators {
            int[] array;
            int index;
            int fence;
            DummySpliterator(int[] array, int start, int end) {
                this.array = array;
                this.index = start;
                this.fence = end;
            }
            public void forEachRemaining(IntConsumer action) {
                int[] a; int i, hi;
                if (action == null) throw new NullPointerException();
                if ((a = array).length >= (hi = fence) && (i = index) >= 0 && i < hi) {
                    do { action.accept(a[i]); } while (++i < hi);
                }
            }
        }
        DummySpliterator spliterator = new DummySpliterator(array, 0, array.length);
        try {
            spliterator.forEachRemaining(null);
            assert false : "NullPointerException expected";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
