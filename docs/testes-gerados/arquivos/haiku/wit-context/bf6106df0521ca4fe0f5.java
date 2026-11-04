/*
 * @test
 * @summary Observable tests for the public ObjectOutputStream(OutputStream) constructor
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testStreamHeaderWritten();
        testStreamHeaderIsDeterministic();
        testRoundTripAfterConstruction();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testStreamHeaderWritten() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.flush();
        byte[] b = bos.toByteArray();
        check(b.length >= 4, "stream header too short: " + b.length);
        check((b[0] & 0xFF) == 0xAC, "bad magic byte 0");
        check((b[1] & 0xFF) == 0xED, "bad magic byte 1");
        check(b[2] == 0x00, "bad version byte 0");
        check(b[3] == 0x05, "bad version byte 1");
        oos.close();
    }

    static void testStreamHeaderIsDeterministic() throws IOException {
        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        ObjectOutputStream oos1 = new ObjectOutputStream(bos1);
        oos1.flush();
        oos1.close();

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(bos2);
        oos2.flush();
        oos2.close();

        check(java.util.Arrays.equals(bos1.toByteArray(), bos2.toByteArray()),
              "headers differ between identical constructions");
    }

    static void testRoundTripAfterConstruction() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeInt(12345);
        oos.writeObject("Today");
        oos.flush();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
        check(ois.readInt() == 12345, "int value mismatch");
        Object o = ois.readObject();
        check("Today".equals(o), "string value mismatch: " + o);
        ois.close();
    }
}
