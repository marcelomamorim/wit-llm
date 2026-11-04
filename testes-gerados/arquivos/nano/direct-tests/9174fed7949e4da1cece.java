/*
 * @test
 * @summary Test for IntStream.tryAdvance method to ensure it correctly advances and applies the action.
 * @run main IntStreamWitupTest
 */
import java.util.stream.IntStream;
import java.util.function.IntConsumer;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testTryAdvanceWithSingleElement();
        testTryAdvanceMultipleCalls();
        testTryAdvanceWithNullAction();
    }

    static void testTryAdvanceWithSingleElement() {
        int seed = 42;
        IntStream stream = IntStream.of(seed);
        final int[] result = new int[1];
        result[0] = -1;
        boolean advanced = stream.tryAdvance(new IntConsumer() {
            @Override
            public void accept(int value) {
                result[0] = value;
            }
        });
        assert advanced : "tryAdvance should return true for single element";
        assert result[0] == seed : "The accepted value should be equal to the seed";
    }

    static void testTryAdvanceMultipleCalls() {
        IntStream stream = IntStream.of(1, 2, 3);
        final StringBuilder sb = new StringBuilder();
        boolean first = stream.tryAdvance(new IntConsumer() {
            @Override
            public void accept(int value) {
                sb.append(value);
            }
        });
        assert first : "First tryAdvance should return true";
        boolean second = stream.tryAdvance(new IntConsumer() {
            @Override
            public void accept(int value) {
                sb.append(",").append(value);
            }
        });
        assert second : "Second tryAdvance should return true";
        boolean third = stream.tryAdvance(new IntConsumer() {
            @Override
            public void accept(int value) {
                sb.append(",").append(value);
            }
        });
        assert third : "Third tryAdvance should return true";
        boolean fourth = stream.tryAdvance(new IntConsumer() {
            @Override
            public void accept(int value) {
                sb.append(",").append(value);
            }
        });
        assert !fourth : "Fourth tryAdvance should return false";
        assert sb.toString().equals("1,2,3") : "Accumulated string should be '1,2,3'";
    }

    static void testTryAdvanceWithNullAction() {
        IntStream stream = IntStream.of(1);
        try {
            stream.tryAdvance(null);
            assert false : "tryAdvance with null should throw NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
