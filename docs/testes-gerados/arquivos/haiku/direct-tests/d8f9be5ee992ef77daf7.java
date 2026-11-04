/*
 * @test
 * @summary Verifies ObjectOutputStream.write(byte[]) writes its bytes as block data that reads back unchanged, and rejects a null array
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testSmallArrayRoundTrip();
        testEmptyArrayKeepsStreamUsable();
        testLargeArrayAcrossBlocks();
        testNullArgumentThrowsNullPointerException();
    }

    private static void testSmallArrayRoundTrip() throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        byte[] read = writeAndReadBytes(data);
        check(Arrays.equals(data, read), "small array round trip");
    }

    private static void testEmptyArrayKeepsStreamUsable() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(new byte[0]);
        oos.writeInt(42);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        ois.readFully(new byte[0]);
        int value = ois.readInt();
        ois.close();
        check(value == 42, "int after empty write expected 42 but was " + value);
    }

    private static void testLargeArrayAcrossBlocks() throws IOException {
        byte[] data = new byte[3000];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) (i * 31);
        }
        byte[] read = writeAndReadBytes(data);
        check(Arrays.equals(data, read), "large array round trip");
    }

    private static void testNullArgumentThrowsNullPointerException() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        boolean thrown = false;
        try {
            oos.write((byte[]) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        oos.close();
        check(thrown, "write(null) should throw NullPointerException");
    }

    private static byte[] writeAndReadBytes(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(data);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        byte[] out = new byte[data.length];
        ois.readFully(out);
        ois.close();
        return out;
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }
}
