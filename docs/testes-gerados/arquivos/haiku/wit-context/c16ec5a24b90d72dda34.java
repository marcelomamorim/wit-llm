/*
 * @test
 * @summary Witup-generated deterministic checks for Random.doubles() no-arg stream
 * @run main RandomWitupTest
 */
import java.util.Random;
import java.util.stream.DoubleStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        Random r1 = new Random(42L);
        Random r2 = new Random(42L);
        double[] a = r1.doubles().limit(200).toArray();
        double[] b = r2.doubles().limit(200).toArray();
        if (a.length != 200) {
            throw new RuntimeException("expected 200 elements, got " + a.length);
        }
        if (b.length != 200) {
            throw new RuntimeException("expected 200 elements, got " + b.length);
        }
        for (int i = 0; i < a.length; i++) {
            if (Double.compare(a[i], b[i]) != 0) {
                throw new RuntimeException("same seed produced different value at index " + i);
            }
            if (!(a[i] >= 0.0 && a[i] < Double.MAX_VALUE)) {
                throw new RuntimeException("value out of range [0, MAX_VALUE) at index " + i + ": " + a[i]);
            }
        }
        DoubleStream s = new Random(7L).doubles();
        if (s.limit(5).count() != 5) {
            throw new RuntimeException("limit(5) did not yield 5 elements");
        }
    }
}
