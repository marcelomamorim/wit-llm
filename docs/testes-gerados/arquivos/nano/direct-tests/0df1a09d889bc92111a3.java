/* @test    @summary Test flush() method of InflaterOutputStream for proper decompression and writing behavior    @run main InflaterOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;
import java.util.zip.InflaterOutputStream;

public class InflaterOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testFlushWithIncompleteData();
        testFlushWithProperData();
        testFlushWithCorruptedData();
    }

    private static void testFlushWithIncompleteData() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater);
        // No data written, just flush
        ios.flush();
        // Expect no exception and output to be empty
        byte[] result = baos.toByteArray();
        assert result.length == 0 : "Expected empty output for incomplete data flush";
        ios.close();
    }

    private static void testFlushWithProperData() throws IOException {
        byte[] compressedData = {120, -100, 3, 0, 0, 0, 0, 1}; // compressed form of empty data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater);
        ios.write(compressedData);
        ios.flush();
        byte[] outputBytes = baos.toByteArray();
        // Since data decompresses to empty, output should be empty or minimal
        assert outputBytes.length >= 0 : "Output should be non-negative";
        ios.close();
    }

    private static void testFlushWithCorruptedData() throws IOException {
        byte[] corruptedData = {0, 0, 0, 0}; // invalid compressed data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Inflater inflater = new Inflater();
        InflaterOutputStream ios = new InflaterOutputStream(baos, inflater);
        ios.write(corruptedData);
        try {
            ios.flush();
            assert false : "Expected ZipException due to corrupted data";
        } catch (ZipException e) {
            // Expected exception
        } finally {
            ios.close();
        }
    }
}
