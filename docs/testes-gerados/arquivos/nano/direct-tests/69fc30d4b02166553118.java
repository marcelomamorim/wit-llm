/*
 * @test
 * @summary Test tryAdvance method of java.util.Spliterators for IntConsumer
 * @run main SpliteratorsWitupTest
 */
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class SpliteratorsWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithValidConsumer();
        testTryAdvanceWithNullConsumer();
        testTryAdvanceWhenIndexOutOfRange();
    }

    static void testTryAdvanceWithValidConsumer() {
        int[] array = {1, 2, 3};
        int[] index = {0};
        IntConsumer consumer = value -> {
            if (index[0] < array.length) {
                assert value == array[index[0]] : "Value mismatch at index " + index[0];
                index[0]++;
            }
        };
        java.util.Spliterators.SpliteratorOfInt spliterator = new java.util.Spliterators.SpliteratorOfInt(array);
        boolean result;
        do {
            result = spliterator.tryAdvance(consumer);
        } while (result);
        assert index[0] == array.length : "Not all elements processed";
    }

    static void testTryAdvanceWithNullConsumer() {
        boolean exceptionThrown = false;
        try {
            java.util.Spliterators.SpliteratorOfInt spliterator = new java.util.Spliterators.SpliteratorOfInt(new int[]{1});
            spliterator.tryAdvance(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "NullPointerException was not thrown for null consumer";
    }

    static void testTryAdvanceWhenIndexOutOfRange() {
        int[] array = {1};
        java.util.Spliterators.SpliteratorOfInt spliterator = new java.util.Spliterators.SpliteratorOfInt(array);
        // Move index to end
        spliterator.tryAdvance(value -> {});
        // Now try advance again, should return false
        boolean result = spliterator.tryAdvance(value -> {});
        assert result == false : "Expected false when index out of range";
    }
}

// Helper class to instantiate SpliteratorOfInt for testing
// Since the original class is package-private, we define a minimal subclass here
class SpliteratorOfInt extends java.util.Spliterators {
    private int[] array;
    private int index = 0;
    private int fence;

    public SpliteratorOfInt(int[] array) {
        this.array = array;
        this.fence = array.length;
    }

    public boolean tryAdvance(java.util.function.IntConsumer action) {
        if (action == null)
            throw new NullPointerException();
        if (index >= 0 && index < fence) {
            action.accept(array[index++]);
            return true;
        }
        return false;
    }
}
