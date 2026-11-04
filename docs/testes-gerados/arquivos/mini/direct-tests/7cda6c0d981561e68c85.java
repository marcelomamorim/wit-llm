/*
 * @test
 * @summary Test StringBuffer.offsetByCodePoints method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer("abc\uD834\uDD1Edef"); // includes a surrogate pair (𝄞)

        // Test offsetByCodePoints with zero offset returns the same index
        int index = 2;
        int result = sb.offsetByCodePoints(index, 0);
        if (result != index) {
            throw new AssertionError("offsetByCodePoints with zero offset should return the same index");
        }

        // Test offsetByCodePoints moving forward by 1 code point
        // index 2 points to 'c', next code point is the surrogate pair at index 3
        int forward = sb.offsetByCodePoints(2, 1);
        if (forward != 3) {
            throw new AssertionError("offsetByCodePoints forward by 1 code point expected 3 but got " + forward);
        }

        // Test offsetByCodePoints moving forward by 2 code points
        // from index 2 ('c') + 2 code points should skip surrogate pair and 'd' at index 5
        int forward2 = sb.offsetByCodePoints(2, 2);
        if (forward2 != 5) {
            throw new AssertionError("offsetByCodePoints forward by 2 code points expected 5 but got " + forward2);
        }

        // Test offsetByCodePoints moving backward by 1 code point
        // from index 3 (start of surrogate pair) back 1 code point should be index 2 ('c')
        int backward = sb.offsetByCodePoints(3, -1);
        if (backward != 2) {
            throw new AssertionError("offsetByCodePoints backward by 1 code point expected 2 but got " + backward);
        }

        // Test offsetByCodePoints moving backward by 2 code points
        // from index 5 ('d') back 2 code points should be index 2 ('c')
        int backward2 = sb.offsetByCodePoints(5, -2);
        if (backward2 != 2) {
            throw new AssertionError("offsetByCodePoints backward by 2 code points expected 2 but got " + backward2);
        }

        // Test offsetByCodePoints at boundary: index 0, offset 0
        int zero = sb.offsetByCodePoints(0, 0);
        if (zero != 0) {
            throw new AssertionError("offsetByCodePoints at start with zero offset expected 0 but got " + zero);
        }

        // Test offsetByCodePoints at boundary: index length, offset 0
        int length = sb.length();
        int zeroEnd = sb.offsetByCodePoints(length, 0);
        if (zeroEnd != length) {
            throw new AssertionError("offsetByCodePoints at end with zero offset expected " + length + " but got " + zeroEnd);
        }

        // No exceptions expected for valid inputs
        System.out.println("All tests passed.");
    }
}
