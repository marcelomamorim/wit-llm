/*
 * @test
 * @summary DeflaterInputStream.reset() always throws IOException because mark/reset is not supported
 * @run main DeflaterInputStreamWitupTest
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        checkResetThrows(new DeflaterInputStream(new ByteArrayInputStream(new byte[]{1, 2, 3})));
        checkResetThrows(new DeflaterInputStream(new ByteArrayInputStream(new byte[0]), new Deflater()));
        checkResetThrows(new DeflaterInputStream(new ByteArrayInputStream(new byte[8]), new Deflater(), 16));

        DeflaterInputStream closed = new DeflaterInputStream(new ByteArrayInputStream(new byte[]{5}));
        closed.close();
        checkResetThrows(closed);
    }

    private static void checkResetThrows(DeflaterInputStream dis) throws IOException {
        try {
            dis.reset();
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("reset() did not throw IOException");
    }
}
