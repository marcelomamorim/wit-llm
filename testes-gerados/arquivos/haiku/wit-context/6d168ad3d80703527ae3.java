/*
 * @test
 * @summary Verifies that PosterOutputStream.write(int) appends bytes before close and ignores writes after close
 * @modules java.base/sun.net.www.http
 * @run main PosterOutputStreamWitupTest
 */

import sun.net.www.http.PosterOutputStream;
import java.io.IOException;

public class PosterOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        PosterOutputStream os = new PosterOutputStream();
        os.write('a');
        os.write('b');
        check(os.size() == 2, "size before close expected 2 but was " + os.size());

        os.close();
        os.write('c');
        check(os.size() == 2, "size after close expected 2 but was " + os.size());

        byte[] data = os.toByteArray();
        check(data.length == 2, "length expected 2 but was " + data.length);
        check(data[0] == 'a' && data[1] == 'b', "content mismatch after close");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
