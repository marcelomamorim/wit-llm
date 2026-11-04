/*
 * @test
 * @summary Verifies that PipedOutputStream constructors and connect() produce a working pipe
 * @run main PipedOutputStreamWitupTest
 */
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipedOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        out.write(65);
        if (in.read() != 65) {
            throw new RuntimeException("single byte mismatch");
        }
        byte[] data = {1, 2, 3};
        out.write(data);
        for (int i = 0; i < data.length; i++) {
            if (in.read() != data[i]) {
                throw new RuntimeException("array byte mismatch at " + i);
            }
        }
        out.close();
        if (in.read() != -1) {
            throw new RuntimeException("expected end of stream after close");
        }

        PipedInputStream in2 = new PipedInputStream();
        PipedOutputStream out2 = new PipedOutputStream();
        out2.connect(in2);
        out2.write(120);
        if (in2.read() != 120) {
            throw new RuntimeException("connect() pipe mismatch");
        }
        out2.close();
        in2.close();
    }
}
