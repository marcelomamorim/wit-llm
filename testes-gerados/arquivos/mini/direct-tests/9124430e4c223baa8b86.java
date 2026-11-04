/*
 * @test
 * @summary Test StringLatin1.compareToUTF16 method behavior
 * @run main StringLatin1WitupTest
 */

public class StringLatin1WitupTest {
    public static void main(String[] args) {
        testCompareToUTF16Basic();
        testCompareToUTF16EmptyArrays();
        testCompareToUTF16LengthBounds();
    }

    private static void testCompareToUTF16Basic() {
        byte[] a = {0, 1, 2, 3, 4};
        byte[] b = {0, 1, 2, 3, 5};

        // len1 and len2 within array lengths
        int result = java.lang.StringLatin1.compareToUTF16(a, b, 5, 5);
        if (result >= 0) {
            throw new Error("Expected a < b, but got " + result);
        }

        // equal arrays
        byte[] c = {10, 20, 30};
        byte[] d = {10, 20, 30};
        int eq = java.lang.StringLatin1.compareToUTF16(c, d, 3, 3);
        if (eq != 0) {
            throw new Error("Expected arrays equal, but got " + eq);
        }

        // a > b
        byte[] e = {5, 6, 7};
        byte[] f = {5, 6, 6};
        int gt = java.lang.StringLatin1.compareToUTF16(e, f, 3, 3);
        if (gt <= 0) {
            throw new Error("Expected e > f, but got " + gt);
        }
    }

    private static void testCompareToUTF16EmptyArrays() {
        byte[] empty1 = new byte[0];
        byte[] empty2 = new byte[0];

        int result = java.lang.StringLatin1.compareToUTF16(empty1, empty2, 0, 0);
        if (result != 0) {
            throw new Error("Expected 0 for empty arrays, got " + result);
        }
    }

    private static void testCompareToUTF16LengthBounds() {
        byte[] arr = {1, 2, 3};
        byte[] arrUTF16 = new byte[6];
        for (int i = 0; i < 6; i++) {
            arrUTF16[i] = (byte) i;
        }

        // len1 == length(value) is valid
        java.lang.StringLatin1.compareToUTF16(arr, arrUTF16, arr.length, 6);

        // len2 == StringUTF16.length(other) is valid
        // We cannot test invalid lengths because checkOffset throws unchecked exceptions
        // but no exception is declared or documented, so we avoid testing invalid inputs here
    }
}
