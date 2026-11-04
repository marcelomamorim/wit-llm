/*
 * @test
 * @summary ObjectOutputStream(OutputStream) writes the serialization stream header on construction
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    private static final int[] HEADER = {0xAC, 0xED, 0x00, 0x05};

    public static void main(String[] args) throws Exception {
        testHeaderWrittenOnConstruction();
        testHeaderIdenticalForNewStreams();
        testWriteIntAfterHeader();
    }

    private static void testHeaderWrittenOnConstruction() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.flush();
        byte[] data = bos.toByteArray();
        check(data.length == 4, "header length must be 4 but was " + data.length);
        checkHeader(data);
        oos.close();
    }

    private static void testHeaderIdenticalForNewStreams() throws Exception {
        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        ObjectOutputStream oos1 = new ObjectOutputStream(bos1);
        ObjectOutputStream oos2 = new ObjectOutputStream(bos2);
        oos1.flush();
        oos2.flush();
        check(java.util.Arrays.equals(bos1.toByteArray(), bos2.toByteArray()),
              "headers of two new streams must be equal");
        oos1.close();
        oos2.close();
    }

    private static void testWriteIntAfterHeader() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(12345);
        oos.flush();
        byte[] data = bos.toByteArray();
        check(data.length > 4, "data after writeInt must extend beyond header");
        byte[] prefix = java.util.Arrays.copyOf(data, 4);
        checkHeader(prefix);
        oos.close();
    }

    private static void checkHeader(byte[] data) {
        for (int i = 0; i < HEADER.length; i++) {
            check((data[i] & 0xFF) == HEADER[i],
                  "header byte " + i + " mismatch: " + (data[i] & 0xFF));
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
