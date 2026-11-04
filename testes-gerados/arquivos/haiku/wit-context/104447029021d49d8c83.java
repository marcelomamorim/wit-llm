/*
 * @test
 * @summary Verify that write(int) of a stream from OutputStream.nullOutputStream() throws IOException after close and does not throw while open
 * @run main OutputStreamWitupTest
 */
import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        OutputStream os = OutputStream.nullOutputStream();
        os.write(1);
        os.write(new byte[] {1, 2, 3}, 0, 3);
        os.flush();
        os.close();
        os.close();
        boolean thrown = false;
        try {
            os.write(2);
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("write(int) after close did not throw IOException");
        }
    }
}
