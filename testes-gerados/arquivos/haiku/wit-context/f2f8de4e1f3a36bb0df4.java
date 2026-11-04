/*
 * @test
 * @summary Indirect checks of ObjectInputStream header validation and back-reference handling
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class ObjectInputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        byte[] badHeader = {0x00, 0x00, 0x00, 0x00};
        boolean thrown = false;
        try {
            new ObjectInputStream(new ByteArrayInputStream(badHeader));
        } catch (StreamCorruptedException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("expected StreamCorruptedException for invalid header");
        }

        String s = "shared";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(s);
            oos.writeObject(s);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()))) {
            Object first = ois.readObject();
            Object second = ois.readObject();
            if (first != second) {
                throw new AssertionError("back-reference must resolve to the same object");
            }
            if (!"shared".equals(first)) {
                throw new AssertionError("unexpected value: " + first);
            }
        }
    }
}
