/*
 * @test
 * @summary PosterOutputStream.write(int) appends bytes before close and ignores them after close
 * @modules java.base/sun.net.www.http
 * @run main PosterOutputStreamWitupTest
 */

import java.util.Arrays;
import sun.net.www.http.PosterOutputStream;

public class PosterOutputStreamWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) throws Exception {
        PosterOutputStream os = new PosterOutputStream();
        check(os.size() == 0, "new stream must be empty");

        os.write(65);
        os.write(66);
        check(os.size() == 2, "size must be 2 after two writes, was " + os.size());
        check(Arrays.equals(os.toByteArray(), new byte[]{65, 66}),
              "content must be [65, 66] before close");

        os.close();
        os.write(67);
        check(os.size() == 2, "write(int) after close must be ignored, size was " + os.size());
        check(Arrays.equals(os.toByteArray(), new byte[]{65, 66}),
              "content must be unchanged after close");

        PosterOutputStream os2 = new PosterOutputStream();
        os2.write(1);
        os2.reset();
        check(os2.size() == 0, "reset before close must clear content");
        os2.write(7);
        check(os2.size() == 1 && os2.toByteArray()[0] == 7,
              "write(int) must work again after reset before close");
    }
}
