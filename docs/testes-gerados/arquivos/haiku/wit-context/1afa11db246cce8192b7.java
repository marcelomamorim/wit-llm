/*
 * @test
 * @summary Verify ObjectInputStream.readFully bounds checking and data transfer
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class ObjectInputStreamWitupTest {

    private static final byte[] PAYLOAD = {1, 2, 3, 4, 5, 6, 7, 8};

    static ObjectInputStream newStream() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.write(PAYLOAD);
        }
        return new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
    }

    static void expectIOOBE(ObjectInputStream in, byte[] buf, int off, int len) throws IOException {
        try {
            in.readFully(buf, off, len);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected IndexOutOfBoundsException off=" + off + " len=" + len);
    }

    public static void main(String[] args) throws IOException {
        byte[] buf = new byte[8];
        newStream().readFully(buf, 0, 8);
        if (!Arrays.equals(buf, PAYLOAD)) {
            throw new AssertionError("readFully returned wrong data");
        }

        expectIOOBE(newStream(), new byte[8], -1, 2);
        expectIOOBE(newStream(), new byte[8], 0, -1);
        expectIOOBE(newStream(), new byte[8], 4, 5);
        expectIOOBE(newStream(), new byte[8], Integer.MAX_VALUE, 2);

        newStream().readFully(new byte[8], 8, 0);
    }
}
