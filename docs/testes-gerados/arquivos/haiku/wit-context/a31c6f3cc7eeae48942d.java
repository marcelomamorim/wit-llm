/*
 * @test
 * @summary Checks the default-inflater constructor of InflaterOutputStream: null output rejection and a deflate/inflate round trip.
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.Deflater;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testNullOutputThrowsNPE();
        testDefaultInflaterRoundTrip();
    }

    static void testNullOutputThrowsNPE() {
        try {
            new InflaterOutputStream(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null output");
    }

    static void testDefaultInflaterRoundTrip() throws IOException {
        byte[] original = "hello inflater output stream".getBytes(StandardCharsets.UTF_8);

        Deflater deflater = new Deflater();
        deflater.setInput(original);
        deflater.finish();
        byte[] tmp = new byte[1024];
        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        while (!deflater.finished()) {
            int n = deflater.deflate(tmp);
            compressed.write(tmp, 0, n);
        }
        deflater.end();

        ByteArrayOutputStream sink = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(sink);
        ios.write(compressed.toByteArray());
        ios.finish();
        ios.close();

        if (!Arrays.equals(original, sink.toByteArray())) {
            throw new RuntimeException("Round trip with default inflater produced different data");
        }
    }
}
