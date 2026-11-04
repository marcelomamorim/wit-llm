/*
 * @test
 * @summary Checks defaultWriteObject outside writeObject throws NotActiveException and works inside writeObject
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
        int value = 42;

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
        }
    }

    public static void main(String[] args) throws Exception {
        testNoActiveContext();
        testInsideWriteObject();
    }

    static void testNoActiveContext() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        try {
            oos.defaultWriteObject();
            throw new AssertionError("expected NotActiveException");
        } catch (NotActiveException expected) {
            // expected
        }
    }

    static void testInsideWriteObject() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(new Holder());
        oos.close();
        byte[] bytes = bos.toByteArray();
        if (bytes.length == 0) {
            throw new AssertionError("no bytes written");
        }
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Holder h = (Holder) ois.readObject();
        if (h.value != 42) {
            throw new AssertionError("unexpected value: " + h.value);
        }
    }
}
