/*
 * @test
 * @summary Witup-derived checks for PosterOutputStream.write(byte[], int, int)
 * @modules java.base/sun.net.www.http
 * @run main PosterOutputStreamWitupTest
 */

import sun.net.www.http.PosterOutputStream;

public class PosterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        validRangeAppendsOnlySelectedBytes();
        invalidRangeThrowsWhenOpen();
        closedStreamIgnoresValidWrites();
        closedStreamIgnoresInvalidRange();
    }

    static void validRangeAppendsOnlySelectedBytes() {
        PosterOutputStream os = new PosterOutputStream();
        byte[] data = {1, 2, 3, 4};
        os.write(data, 1, 2);
        byte[] out = os.toByteArray();
        check(out.length == 2, "expected 2 bytes");
        check(out[0] == 2 && out[1] == 3, "expected bytes {2,3}");
    }

    static void invalidRangeThrowsWhenOpen() {
        byte[] data = {1, 2, 3, 4};
        int[][] cases = { {-1, 1}, {0, -1}, {3, 2}, {5, 0} };
        for (int[] c : cases) {
            PosterOutputStream os = new PosterOutputStream();
            boolean thrown = false;
            try {
                os.write(data, c[0], c[1]);
            } catch (IndexOutOfBoundsException e) {
                thrown = true;
            }
            check(thrown, "expected IndexOutOfBoundsException for off=" + c[0] + " len=" + c[1]);
            check(os.toByteArray().length == 0, "no bytes should be written on failed write");
        }
    }

    static void closedStreamIgnoresValidWrites() throws Exception {
        PosterOutputStream os = new PosterOutputStream();
        os.close();
        byte[] data = {1, 2, 3, 4};
        os.write(data, 0, 4);
        check(os.toByteArray().length == 0, "closed stream must ignore writes");
    }

    static void closedStreamIgnoresInvalidRange() throws Exception {
        PosterOutputStream os = new PosterOutputStream();
        os.close();
        byte[] data = {1, 2, 3, 4};
        os.write(data, -1, 1);
        check(os.toByteArray().length == 0, "closed stream must return early without writing");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
