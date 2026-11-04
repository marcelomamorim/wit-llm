/*
 * @test
 * @summary Test DeflaterInputStream(InputStream in) constructor behavior
 * @run main DeflaterInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.DeflaterInputStream;

public class DeflaterInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Test with valid non-empty input stream
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DeflaterInputStream dis = new DeflaterInputStream(bais);
        // Read all bytes to ensure no exception and stream is functional
        int totalRead = 0;
        byte[] buffer = new byte[10];
        int n;
        while ((n = dis.read(buffer)) != -1) {
            totalRead += n;
        }
        if (totalRead < 0) {
            throw new AssertionError("Read negative bytes");
        }
        dis.close();

        // Test with empty input stream
        ByteArrayInputStream emptyBais = new ByteArrayInputStream(new byte[0]);
        DeflaterInputStream disEmpty = new DeflaterInputStream(emptyBais);
        int readEmpty = disEmpty.read();
        if (readEmpty != -1) {
            throw new AssertionError("Expected EOF (-1) on empty stream");
        }
        disEmpty.close();

        // Test with null input stream - expect NullPointerException
        try {
            new DeflaterInputStream(null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
