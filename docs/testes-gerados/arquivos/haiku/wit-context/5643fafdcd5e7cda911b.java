/*
 * @test
 * @summary Witup-derived behavioral tests for Arrays.equals(Object[], Object[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        Object[] a = new Object[]{"x", 1, null};

        if (!Arrays.equals(a, a)) {
            throw new RuntimeException("same reference must be equal");
        }
        if (!Arrays.equals((Object[]) null, (Object[]) null)) {
            throw new RuntimeException("null, null must be equal");
        }
        if (Arrays.equals(a, (Object[]) null)) {
            throw new RuntimeException("non-null vs null must not be equal");
        }
        if (Arrays.equals((Object[]) null, a)) {
            throw new RuntimeException("null vs non-null must not be equal");
        }
        if (!Arrays.equals(new Object[]{"x", 1, null}, new Object[]{"x", 1, null})) {
            throw new RuntimeException("equal contents with null element must be equal");
        }
        if (!Arrays.equals(new Object[0], new Object[0])) {
            throw new RuntimeException("two empty arrays must be equal");
        }
        if (!Arrays.equals(new Object[]{null}, new Object[]{null})) {
            throw new RuntimeException("single null elements must be equal");
        }
        if (Arrays.equals(new Object[]{"x"}, new Object[]{"x", "y"})) {
            throw new RuntimeException("different lengths must not be equal");
        }
        if (Arrays.equals(new Object[]{"x"}, new Object[]{"y"})) {
            throw new RuntimeException("different elements must not be equal");
        }
        if (Arrays.equals(new Object[]{null}, new Object[]{"x"})) {
            throw new RuntimeException("null vs non-null element must not be equal");
        }
        if (Arrays.equals(new Object[]{1}, new Object[]{1L})) {
            throw new RuntimeException("Integer(1) and Long(1) must not be equal");
        }
    }
}
