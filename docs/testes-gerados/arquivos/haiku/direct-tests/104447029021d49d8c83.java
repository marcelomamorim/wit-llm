/*
 * @test
 * @summary Verifies the observable write(int) behavior of the stream returned by OutputStream.nullOutputStream(), including the closed-stream IOException.
 * @run main OutputStreamWitupTest
 */

import java.io.IOException;
import java.io.OutputStream;

public class OutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        OutputStream os = OutputStream.nullOutputStream();

        os.write(1);
        os.write(255);
        os.write(new byte[] {1, 2, 3}, 0, 3);
        os.flush();

        try {
            os.write(new byte[2], 0, 5);
            throw new RuntimeException("expected IndexOutOfBoundsException for invalid range");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }

        os.close();
        os.close();

        try {
            os.write(1);
            throw new RuntimeException("write(int) did not throw after close");
        } catch (IOException e) {
            if (!"Stream closed".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }

        try {
            os.write(new byte[] {1}, 0, 1);
            throw new RuntimeException("write(byte[],int,int) did not throw after close");
        } catch (IOException e) {
            if (!"Stream closed".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }
}
