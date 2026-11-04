/*
 * @test
 * @summary Checks public readFully(byte[], int, int) behavior of ObjectInputStream in block data mode
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testReadsExactBlockData();
        testZeroLengthReadDoesNotFail();
        testReadPastEndThrowsEOFException();
        System.out.println("PASS");
    }

    private static void testReadsExactBlockData() throws Exception {
        byte[] payload = {1, 2, 3, 4, 5, 6, 7, 8};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(payload);
        oos.flush();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        byte[] out = new byte[payload.length];
        ois.readFully(out, 0, out.length);
        for (int i = 0; i < payload.length; i++) {
            if (out[i] != payload[i]) {
                throw new RuntimeException("Mismatch at index " + i + ": expected "
                        + payload[i] + " but was " + out[i]);
            }
        }
        ois.close();
    }

    private static void testZeroLengthReadDoesNotFail() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(new byte[] {9, 9});
        oos.flush();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        ois.readFully(new byte[0], 0, 0);
        byte[] out = new byte[2];
        ois.readFully(out, 0, 2);
        if (out[0] != 9 || out[1] != 9) {
            throw new RuntimeException("Unexpected data after zero-length read");
        }
        ois.close();
    }

    private static void testReadPastEndThrowsEOFException() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.write(new byte[] {1, 2});
        oos.flush();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        byte[] out = new byte[2];
        ois.readFully(out, 0, 2);
        try {
            ois.readFully(new byte[1], 0, 1);
            throw new RuntimeException("Expected EOFException when reading past end of block data");
        } catch (EOFException expected) {
            // expected
        }
        ois.close();
    }
}
