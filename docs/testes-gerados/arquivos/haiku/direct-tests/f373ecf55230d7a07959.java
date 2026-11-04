/*
 * @test
 * @summary Verify Arrays.equals(short[], short[]) on identity, null, length, and content differences
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {

    public static void main(String[] args) {
        short[] a = {1, 2, 3};
        short[] same = a;
        short[] equalCopy = {1, 2, 3};
        short[] differentValue = {1, 2, 4};
        short[] shorter = {1, 2};
        short[] longer = {1, 2, 3, 4};
        short[] empty1 = new short[0];
        short[] empty2 = new short[0];
        short[] nul = null;
        short[] extremes1 = {Short.MIN_VALUE, Short.MAX_VALUE, 0, -1};
        short[] extremes2 = {Short.MIN_VALUE, Short.MAX_VALUE, 0, -1};
        short[] extremesDiff = {Short.MIN_VALUE, Short.MAX_VALUE, 0, 1};

        check(java.util.Arrays.equals(a, same), "identical reference should be equal");
        check(java.util.Arrays.equals(a, equalCopy), "equal contents should be equal");
        check(java.util.Arrays.equals(equalCopy, a), "equality should be symmetric");
        check(java.util.Arrays.equals(nul, nul), "null,null should be equal");
        check(!java.util.Arrays.equals(a, nul), "array vs null should not be equal");
        check(!java.util.Arrays.equals(nul, a), "null vs array should not be equal");
        check(!java.util.Arrays.equals(a, shorter), "shorter array should not be equal");
        check(!java.util.Arrays.equals(shorter, a), "shorter vs longer should not be equal");
        check(!java.util.Arrays.equals(a, longer), "longer array should not be equal");
        check(!java.util.Arrays.equals(a, differentValue), "differing element should not be equal");
        check(java.util.Arrays.equals(empty1, empty2), "two empty arrays should be equal");
        check(!java.util.Arrays.equals(empty1, a), "empty vs non-empty should not be equal");
        check(java.util.Arrays.equals(extremes1, extremes2), "boundary values should compare by content");
        check(!java.util.Arrays.equals(extremes1, extremesDiff), "boundary value difference should be detected");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
