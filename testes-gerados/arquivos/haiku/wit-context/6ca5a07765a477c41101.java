/*
 * @test
 * @summary Witup-generated checks for Random.ints(int,int): bad range rejection, value bounds and determinism
 * @run main RandomWitupTest
 */
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        checkIllegalRange(5, 5);
        checkIllegalRange(10, 3);
        checkIllegalRange(0, 0);

        int[] zeros = new Random(1L).ints(0, 1).limit(20).toArray();
        if (zeros.length != 20) {
            throw new RuntimeException("unexpected length: " + zeros.length);
        }
        for (int v : zeros) {
            if (v != 0) {
                throw new RuntimeException("value outside [0,1): " + v);
            }
        }

        int[] a = new Random(42L).ints(-5, 5).limit(1000).toArray();
        int[] b = new Random(42L).ints(-5, 5).limit(1000).toArray();
        if (!Arrays.equals(a, b)) {
            throw new RuntimeException("same seed produced different sequences");
        }
        for (int v : a) {
            if (v < -5 || v >= 5) {
                throw new RuntimeException("value outside [-5,5): " + v);
            }
        }
    }

    static void checkIllegalRange(int origin, int bound) {
        boolean thrown = false;
        try {
            IntStream s = new Random(7L).ints(origin, bound);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IllegalArgumentException for origin="
                    + origin + ", bound=" + bound);
        }
    }
}
