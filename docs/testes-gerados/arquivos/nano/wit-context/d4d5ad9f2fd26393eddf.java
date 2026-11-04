/*
 * @test
 * @summary Test for java.io.FilterOutputStream.write(byte[])
 * @run main FilterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.FilterOutputStream;

public class FilterOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testWriteByteArrayNormal();
        testWriteByteArrayEmpty();
        testWriteByteArrayNull();
        testWriteByteArrayIndexOutOfBounds();
    }

    static void testWriteByteArrayNormal() throws IOException {
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

    static void testWriteByteArrayEmpty() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FilterOutputStream fos = new FilterOutputStream(baos);
        byte[] data = {};
        fos.write(data);
        fos.flush();
        byte[] result = baos.toByteArray();
        assert result.length == 0 : "Expected empty array";
        fos.close();
    }

    static void testWriteByteArrayNull() {
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

    static void testWriteByteArrayIndexOutOfBounds() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FilterOutputStream fos = new FilterOutputStream(baos);
        byte[] data = {1, 2, 3};
        try {
            // Triggering the exception by calling write with invalid offset and length
            // Since write(byte[], int, int) is not overridden, it calls the superclass method,
            // which throws IndexOutOfBoundsException if parameters are invalid.
            fos.write(data, -1, 2);
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        } finally {
            fos.close();
        }
    }
}
