/*
 * @test
 * @summary Test java.util.Arrays.binarySearch(short[], short) for correctness and exception paths
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testBinarySearchBasic();
        testBinarySearchExceptions();
        System.out.println("All tests passed.");
    }

    private static void testBinarySearchBasic() {
        short[] arr = {1, 3, 5, 7, 9};

        // Test existing keys
        int idx = java.util.Arrays.binarySearch(arr, (short)3);
        if (idx != 1) throw new RuntimeException("Expected index 1 for key 3, got " + idx);

        idx = java.util.Arrays.binarySearch(arr, (short)9);
        if (idx != 4) throw new RuntimeException("Expected index 4 for key 9, got " + idx);

        // Test non-existing key (should return negative insertion point -1)
        idx = java.util.Arrays.binarySearch(arr, (short)4);
        if (idx >= 0) throw new RuntimeException("Expected negative index for key 4, got " + idx);

        // Test empty array
        short[] empty = new short[0];
        idx = java.util.Arrays.binarySearch(empty, (short)1);
        if (idx >= 0) throw new RuntimeException("Expected negative index for empty array, got " + idx);
    }

    private static void testBinarySearchExceptions() {
        short[] arr = {1, 2, 3};

        // The public binarySearch(short[], short) calls binarySearch0 with fromIndex=0 and toIndex=arr.length
        // So exceptions from invalid fromIndex/toIndex cannot be triggered via this method directly.
        // Hence, no exceptions expected from this method.
        // We test that no exception is thrown for normal input.

        try {
            java.util.Arrays.binarySearch(arr, (short)2);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception for valid input", e);
        }

        // The expaths for exceptions relate to internal calls with invalid fromIndex/toIndex,
        // which cannot be triggered via this public method.
        // Therefore, no tests for exceptions are possible here without reflection or internal access.
    }
}
