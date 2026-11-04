/*
 * @test
 * @summary Test java.lang.StringLatin1.fillNull method
 * @run main StringLatin1WitupTest
 */

import java.util.Arrays;

public class StringLatin1WitupTest {
    public static void main(String[] args) {
        testFillNullBasic();
        testFillNullPartial();
        testFillNullEmptyRange();
        testFillNullFullArray();
    }

    private static void testFillNullBasic() {
        byte[] arr = new byte[] {1, 2, 3, 4, 5};
        java.lang.StringLatin1.fillNull(arr, 1, 4);
        // indices 1,2,3 should be zero
        if (arr[0] != 1) throw new RuntimeException("testFillNullBasic failed at index 0");
        if (arr[1] != 0) throw new RuntimeException("testFillNullBasic failed at index 1");
        if (arr[2] != 0) throw new RuntimeException("testFillNullBasic failed at index 2");
        if (arr[3] != 0) throw new RuntimeException("testFillNullBasic failed at index 3");
        if (arr[4] != 5) throw new RuntimeException("testFillNullBasic failed at index 4");
    }

    private static void testFillNullPartial() {
        byte[] arr = new byte[] {10, 20, 30, 40, 50};
        java.lang.StringLatin1.fillNull(arr, 2, 3);
        // only index 2 should be zero
        if (arr[0] != 10) throw new RuntimeException("testFillNullPartial failed at index 0");
        if (arr[1] != 20) throw new RuntimeException("testFillNullPartial failed at index 1");
        if (arr[2] != 0) throw new RuntimeException("testFillNullPartial failed at index 2");
        if (arr[3] != 40) throw new RuntimeException("testFillNullPartial failed at index 3");
        if (arr[4] != 50) throw new RuntimeException("testFillNullPartial failed at index 4");
    }

    private static void testFillNullEmptyRange() {
        byte[] arr = new byte[] {7, 8, 9};
        java.lang.StringLatin1.fillNull(arr, 1, 1);
        // no change expected
        if (arr[0] != 7) throw new RuntimeException("testFillNullEmptyRange failed at index 0");
        if (arr[1] != 8) throw new RuntimeException("testFillNullEmptyRange failed at index 1");
        if (arr[2] != 9) throw new RuntimeException("testFillNullEmptyRange failed at index 2");
    }

    private static void testFillNullFullArray() {
        byte[] arr = new byte[] {100, 101, 102};
        java.lang.StringLatin1.fillNull(arr, 0, arr.length);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) throw new RuntimeException("testFillNullFullArray failed at index " + i);
        }
    }
}
