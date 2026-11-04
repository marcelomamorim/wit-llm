/*
 * @test
 * @summary ObjectOutputStream.defaultWriteObject throws NotActiveException outside writeObject and writes fields inside writeObject
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectOutputStreamWitupTest {

    static class Holder implements Serializable {
        private static final long serialVersionUID = 1L;
        private int value;
        private String name;

        Holder(int value, String name) {
            this.value = value;
            this.name = name;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
        }
    }

    public static void main(String[] args) throws Exception {
        testOutsideWriteObjectThrows();
        testInsideWriteObjectRoundTrip();
    }

    static void testOutsideWriteObjectThrows() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        boolean thrown = false;
        try {
            oos.defaultWriteObject();
        } catch (NotActiveException e) {
            thrown = true;
            if (!"not in call to writeObject".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
        if (!thrown) {
            throw new RuntimeException("NotActiveException expected outside writeObject");
        }
    }

    static void testInsideWriteObjectRoundTrip() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new Holder(42, "abc"));
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        Holder h = (Holder) ois.readObject();
        ois.close();

        if (h.value != 42) {
            throw new RuntimeException("Expected value 42 but got " + h.value);
        }
        if (!"abc".equals(h.name)) {
            throw new RuntimeException("Expected name abc but got " + h.name);
        }
    }
}
