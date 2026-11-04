/*
 * @test
 * @summary Test IntStream.generate with a simple IntSupplier
 * @run main IntStreamWitupTest
 */
import java.util.stream.IntStream;
import java.util.function.IntSupplier;

public class IntStreamWitupTest {

    public static void main(String[] args) {
        testGenerateWithCounter();
        testGenerateWithNullSupplier();
    }

    static void testGenerateWithCounter() {
        IntSupplier supplier = new IntSupplier() {
            private int count = 0;
            @Override
            public int getAsInt() {
                return count++;
            }
        };
        IntStream stream = IntStream.generate(supplier);
        int[] result = stream.limit(5).toArray();
        assert result.length == 5 : "Expected length 5";
        for (int i = 0; i < 5; i++) {
            assert result[i] == i : "Expected " + i + " but got " + result[i];
        }
    }

    static void testGenerateWithNullSupplier() {
        try {
            IntStream stream = IntStream.generate(null);
            stream.limit(1).toArray();
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
