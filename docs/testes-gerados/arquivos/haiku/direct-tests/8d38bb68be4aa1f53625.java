/*
 * @test
 * @summary Tests basic behavior of Arrays.equals(byte[], byte[])
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {

    public static void main(String[] args) {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 2, 3};
        check(java.util.Arrays.equals(a, a), true, "same reference");
        check(java.util.Arrays.equals(a, b), true, "equal contents");
        check(java.util.Arrays.equals((byte[]) null, (byte[]) null), true, "both null");
        check(java.util.Arrays.equals(a, (byte[]) null), false, "first array vs null");
        check(java.util.Arrays.equals((byte[]) null, b), false, "null vs second array");
        check(java.util.Arrays.equals(new byte[0], new byte[0]), true, "empty arrays");
        check(java.util.Arrays.equals(a, new byte[]{1, 2}), false, "different lengths");
        check(java.util.Arrays.equals(a, new byte[]{1, 2, 4}), false, "difference at last element");
        check(java.util.Arrays.equals(new byte[]{-1}, new byte[]{-1}), true, "negative values equal");
        check(java.util.Arrays.equals(new byte[]{-1}, new byte[]{1}), false, "sign differs");
    }

    static void check(boolean actual, boolean expected, String msg) {
        if (actual != expected) {
            throw new RuntimeException(msg + ": expected " + expected + " but was " + actual);
        }
    }
}
