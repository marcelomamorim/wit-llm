/*
 * @test
 * @summary Test StringLatin1.inflate(byte[], int, int) method
 * @run main java.lang.StringLatin1WitupTest
 */

public class StringLatin1WitupTest {
    public static void main(String[] args) {
        testInflateBasic();
        testInflateEmpty();
        testInflatePartial();
    }

    private static void testInflateBasic() {
        byte[] input = {0, 1, 2, 3, 4, 5};
        int off = 0;
        int len = input.length;
        byte[] result = java.lang.StringLatin1.inflate(input, off, len);
        if (result == null) {
            throw new RuntimeException("inflate returned null");
        }
        if (result.length != len * 2) {
            throw new RuntimeException("inflate result length incorrect: expected " + (len * 2) + " but got " + result.length);
        }
        for (int i = 0; i < len; i++) {
            if (result[2 * i] != 0 || result[2 * i + 1] != (input[i] & 0xFF)) {
                throw new RuntimeException("inflate result content incorrect at index " + i);
            }
        }
    }

    private static void testInflateEmpty() {
        byte[] input = new byte[0];
        int off = 0;
        int len = 0;
        byte[] result = java.lang.StringLatin1.inflate(input, off, len);
        if (result == null) {
            throw new RuntimeException("inflate returned null for empty input");
        }
        if (result.length != 0) {
            throw new RuntimeException("inflate result length incorrect for empty input: expected 0 but got " + result.length);
        }
    }

    private static void testInflatePartial() {
        byte[] input = {10, 20, 30, 40, 50};
        int off = 1;
        int len = 3;
        byte[] result = java.lang.StringLatin1.inflate(input, off, len);
        if (result == null) {
            throw new RuntimeException("inflate returned null for partial input");
        }
        if (result.length != len * 2) {
            throw new RuntimeException("inflate result length incorrect for partial input: expected " + (len * 2) + " but got " + result.length);
        }
        for (int i = 0; i < len; i++) {
            if (result[2 * i] != 0 || result[2 * i + 1] != (input[off + i] & 0xFF)) {
                throw new RuntimeException("inflate result content incorrect at index " + i + " for partial input");
            }
        }
    }
}
