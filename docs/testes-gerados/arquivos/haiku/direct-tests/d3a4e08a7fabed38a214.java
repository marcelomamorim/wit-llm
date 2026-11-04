/*
 * @test
 * @summary Tests Arrays.equals(boolean[], boolean[]) for identity, null, length and content behavior
 * @run main ArraysWitupTest
 */
import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        boolean[] a = {true, false, true};
        boolean[] same = a;

        check(Arrays.equals(a, same), "identical reference should be equal");
        check(Arrays.equals(null, null), "null, null should be equal");
        check(!Arrays.equals(a, null), "non-null vs null should not be equal");
        check(!Arrays.equals(null, a), "null vs non-null should not be equal");

        check(Arrays.equals(new boolean[0], new boolean[0]), "two empty arrays should be equal");
        check(Arrays.equals(a, new boolean[]{true, false, true}), "arrays with same contents should be equal");

        check(!Arrays.equals(a, new boolean[]{true, false}), "different lengths should not be equal");
        check(!Arrays.equals(new boolean[]{true}, new boolean[]{true, true}), "different lengths (longer) should not be equal");
        check(!Arrays.equals(new boolean[0], new boolean[]{false}), "empty vs non-empty should not be equal");

        check(!Arrays.equals(new boolean[]{false, false, false}, new boolean[]{true, false, false}),
              "difference at first index should be detected");
        check(!Arrays.equals(new boolean[]{false, true, false}, new boolean[]{false, false, false}),
              "difference at middle index should be detected");
        check(!Arrays.equals(new boolean[]{false, false, true}, new boolean[]{false, false, false}),
              "difference at last index should be detected");

        boolean[] big1 = new boolean[1000];
        boolean[] big2 = new boolean[1000];
        check(Arrays.equals(big1, big2), "large equal arrays should be equal");
        big2[999] = true;
        check(!Arrays.equals(big1, big2), "large arrays differing at end should not be equal");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
