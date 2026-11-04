/*
 * @test
 * @summary InflaterInputStream.reset() must always throw IOException with message "mark/reset not supported"
 * @run main InflaterInputStreamWitupTest
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class InflaterInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        InputStream in = new InputStream() {
            @Override
            public int read() {
                return -1;
            }
        };

        InflaterInputStream iis = new InflaterInputStream(in);
        checkResetThrows(iis);
        checkResetThrows(iis);

        InflaterInputStream iis2 = new InflaterInputStream(in, new Inflater(), 16);
        checkResetThrows(iis2);

        InflaterInputStream iis3 = new InflaterInputStream(in, new Inflater());
        checkResetThrows(iis3);
    }

    private static void checkResetThrows(InflaterInputStream s) {
        try {
            s.reset();
        } catch (IOException e) {
            if (!"mark/reset not supported".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("reset() did not throw IOException");
    }
}
