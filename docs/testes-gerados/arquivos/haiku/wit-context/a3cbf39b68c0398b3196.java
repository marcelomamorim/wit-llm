/*
 * @test
 * @summary Verifies LongStream.generate rejects a null supplier and produces the values supplied by the given LongSupplier
 * @run main LongStreamWitupTest
 */
import java.util.stream.LongStream;

public class LongStreamWitupTest {
    public static void main(String[] args) {
        try {
            LongStream.generate(null);
            throw new AssertionError("expected NullPointerException for null supplier");
        } catch (NullPointerException expected) {
        }

        long[] counter = {0L};
        long[] values = LongStream.generate(() -> counter[0]++).limit(4).toArray();
        long[] expectedValues = {0L, 1L, 2L, 3L};
        if (values.length != expectedValues.length) {
            throw new AssertionError("unexpected length: " + values.length);
        }
        for (int i = 0; i < expectedValues.length; i++) {
            if (values[i] != expectedValues[i]) {
                throw new AssertionError("unexpected value at " + i + ": " + values[i]);
            }
        }

        long[] constant = LongStream.generate(() -> 42L).limit(3).toArray();
        if (constant.length != 3) {
            throw new AssertionError("unexpected length for constant supplier: " + constant.length);
        }
        for (long v : constant) {
            if (v != 42L) {
                throw new AssertionError("unexpected constant value: " + v);
            }
        }
    }
}
