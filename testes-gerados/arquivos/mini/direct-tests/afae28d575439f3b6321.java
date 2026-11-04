/*
 * @test
 * @summary Test String.offsetByCodePoints(int index, int codePointOffset) method
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testValidOffsets();
        testIndexOutOfBounds();
    }

    private static void testValidOffsets() {
        String s = "a\uD834\uDD1Eb"; // 'a' + 1 supplementary char (𝄞) + 'b'
        // length() == 4 (chars), code points == 3

        // index at 0, offset 0 -> 0
        int res = s.offsetByCodePoints(0, 0);
        if (res != 0) throw new RuntimeException("Failed: expected 0 but got " + res);

        // index at 0, offset 1 -> 1 (start of supplementary char)
        res = s.offsetByCodePoints(0, 1);
        if (res != 1) throw new RuntimeException("Failed: expected 1 but got " + res);

        // index at 0, offset 2 -> 3 (start of 'b')
        res = s.offsetByCodePoints(0, 2);
        if (res != 3) throw new RuntimeException("Failed: expected 3 but got " + res);

        // index at 1 (start of supplementary char), offset 1 -> 3
        res = s.offsetByCodePoints(1, 1);
        if (res != 3) throw new RuntimeException("Failed: expected 3 but got " + res);

        // index at 3 (start of 'b'), offset -2 -> 0
        res = s.offsetByCodePoints(3, -2);
        if (res != 0) throw new RuntimeException("Failed: expected 0 but got " + res);

        // index at length() == 4, offset 0 -> 4
        res = s.offsetByCodePoints(4, 0);
        if (res != 4) throw new RuntimeException("Failed: expected 4 but got " + res);
    }

    private static void testIndexOutOfBounds() {
        String s = "abc";
        boolean thrown;

        // index < 0
        thrown = false;
        try {
            s.offsetByCodePoints(-1, 0);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("Failed: expected IndexOutOfBoundsException for index -1");

        // index > length()
        thrown = false;
        try {
            s.offsetByCodePoints(s.length() + 1, 0);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) throw new RuntimeException("Failed: expected IndexOutOfBoundsException for index > length");
    }
}
