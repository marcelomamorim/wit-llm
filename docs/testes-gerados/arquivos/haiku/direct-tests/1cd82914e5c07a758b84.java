/*
 * @test
 * @summary ObjectOutputStream.write(byte[],int,int) argument checks and block data round trip
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        byte[] data = {1, 2, 3, 4, 5};

        expectNpe(oos);
        expectIoobe(oos, data, -1, 2);
        expectIoobe(oos, data, 0, -1);
        expectIoobe(oos, data, 3, 3);
        expectIoobe(oos, data, Integer.MAX_VALUE, 1);

        oos.write(data, 5, 0);
        oos.write(data, 1, 3);
        oos.flush();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        byte[] out = new byte[3];
        ois.readFully(out);
        if (out[0] != 2 || out[1] != 3 || out[2] != 4) {
            throw new AssertionError("unexpected payload read back");
        }
        ois.close();
    }

    static void expectNpe(ObjectOutputStream oos) throws IOException {
        try {
            oos.write(null, 0, 0);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException");
    }

    static void expectIoobe(ObjectOutputStream oos, byte[] buf, int off, int len) throws IOException {
        try {
            oos.write(buf, off, len);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("expected IndexOutOfBoundsException");
    }
}
