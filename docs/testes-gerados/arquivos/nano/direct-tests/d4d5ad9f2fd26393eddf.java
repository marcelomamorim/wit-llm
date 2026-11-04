/*
 * @test
 * @summary Test the write(byte b[]) method of FilterOutputStream.
 * @run main FilterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.FilterOutputStream;

public class FilterOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteByteArray();
        testWriteEmptyByteArray();
        testWriteNullByteArray();
    }

    static void testWriteByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FilterOutputStream fos = new FilterOutputStream(baos);
        byte[] data = {1, 2, 3, 4, 5};
        fos.write(data);
        fos.flush();
        byte[] result = baos.toByteArray();
        assert result.length == data.length : "Length mismatch";
        for (int i = 0; i < data.length; i++) {
            assert result[i] == data[i] : "Data mismatch at index " + i;
        }
        fos.close();
    }

    static void testWriteEmptyByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FilterOutputStream fos = new FilterOutputStream(baos);
        byte[] data = {};
        fos.write(data);
        fos.flush();
        byte[] result = baos.toByteArray();
        assert result.length == 0 : "Expected empty array";
        fos.close();
    }

    static void testWriteNullByteArray() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FilterOutputStream fos = new FilterOutputStream(baos);
        try {
            fos.write((byte[]) null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
