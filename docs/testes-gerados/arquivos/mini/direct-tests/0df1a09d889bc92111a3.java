/*
 * @test
 * @summary Test InflaterOutputStream.flush() behavior including normal and error cases
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;
import java.util.zip.ZipException;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testFlushNormal();
        testFlushClosedStream();
        testFlushDataFormatException();
    }

    // Test flush on a normal InflaterOutputStream with default Inflater
    private static void testFlushNormal() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos);

        // flush should not throw and should flush underlying stream
        ios.flush();

        ios.close();
    }

    // Test flush after close throws IOException
    private static void testFlushClosedStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos);
        try {
            ios.close();
            ios.flush();
            throw new RuntimeException("Expected IOException on flush after close");
        } catch (IOException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }

    // Test flush triggers ZipException when Inflater.inflate throws DataFormatException
    private static void testFlushDataFormatException() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Create Inflater that always throws DataFormatException on inflate
        Inflater badInflater = new Inflater() {
            @Override
            public int inflate(byte[] b, int off, int len) throws DataFormatException {
                throw new DataFormatException("test data format error");
            }

            @Override
            public boolean finished() {
                return false;
            }

            @Override
            public boolean needsInput() {
                return false;
            }
        };

        InflaterOutputStream ios = new InflaterOutputStream(baos, badInflater);

        try {
            ios.flush();
            throw new RuntimeException("Expected ZipException from flush");
        } catch (ZipException ze) {
            String msg = ze.getMessage();
            if (!"test data format error".equals(msg)) {
                throw new RuntimeException("Unexpected ZipException message: " + msg);
            }
        }

        ios.close();
    }
}
