/*
 * @test
 * @summary Test ZipInputStream.closeEntry() behavior
 * @run main ZipInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

public class ZipInputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        testCloseEntryOnEmptyStream();
        testCloseEntryOnNonEmptyStream();
        testCloseEntryAfterClose();
    }

    private static void testCloseEntryOnEmptyStream() throws IOException {
        byte[] emptyZip = new byte[0];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(emptyZip));
        // closeEntry should set entryEOF to true without error even if no data
        zis.closeEntry();
    }

    private static void testCloseEntryOnNonEmptyStream() throws IOException {
        // Provide a stream with some data so read(tmpbuf,0,tmpbuf.length) returns > -1
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(data));
        // closeEntry should consume all data and set entryEOF to true
        zis.closeEntry();
    }

    private static void testCloseEntryAfterClose() {
        try {
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]));
            zis.close();
            zis.closeEntry();
            throw new RuntimeException("Expected IOException not thrown after close");
        } catch (IOException e) {
            // Expected IOException: Stream closed
            String msg = e.getMessage();
            if (msg == null || !msg.equals("Stream closed")) {
                throw new RuntimeException("Unexpected IOException message: " + msg);
            }
        }
    }
}
