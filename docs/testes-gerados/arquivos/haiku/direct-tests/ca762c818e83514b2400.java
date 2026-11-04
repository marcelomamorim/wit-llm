/*
 * @test
 * @summary Verify PosterOutputStream.write(byte[],int,int) writes while open and is ignored after close
 * @modules java.base/sun.net.www.http
 * @run main PosterOutputStreamWitupTest
 */

import sun.net.www.http.PosterOutputStream;

public class PosterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        byte[] data = {1, 2, 3, 4, 5};

        PosterOutputStream out = new PosterOutputStream();
        out.write(data, 1, 3);
        check(out.size() == 3, "size after open write should be 3");
        byte[] content = out.toByteArray();
        check(content.length == 3 && content[0] == 2 && content[1] == 3 && content[2] == 4,
              "content should be bytes 2,3,4");

        out.write(data, 0, 0);
        check(out.size() == 3, "zero-length write should not change size");

        out.close();
        out.write(data, 0, 5);
        check(out.size() == 3, "write after close should be ignored");

        out.write(data, 99, 99);
        check(out.size() == 3, "invalid-range write after close should be ignored without exception");

        PosterOutputStream open = new PosterOutputStream();
        boolean thrown = false;
        try {
            open.write(data, 4, 5);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "invalid-range write on open stream should throw IndexOutOfBoundsException");
        check(open.size() == 0, "failed write should not add bytes");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
