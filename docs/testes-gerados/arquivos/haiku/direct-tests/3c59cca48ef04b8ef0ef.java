/*
 * @test
 * @summary ObjectOutputStream.putFields() must throw NotActiveException outside writeObject and return the same PutField instance within writeObject
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectOutputStreamWitupTest {

    static volatile ObjectOutputStream.PutField first;
    static volatile ObjectOutputStream.PutField second;

    static class Holder implements Serializable {
        private static final long serialVersionUID = 1L;
        private int x = 7;

        private void writeObject(ObjectOutputStream out) throws IOException {
            first = out.putFields();
            second = out.putFields();
            out.writeFields();
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        boolean thrown = false;
        try {
            oos.putFields();
        } catch (NotActiveException e) {
            thrown = true;
        }
        check(thrown, "putFields() outside writeObject should throw NotActiveException");

        oos.writeObject(new Holder());

        check(first != null, "putFields() inside writeObject returned null");
        check(first == second, "repeated putFields() calls should return the same instance");
        oos.close();
    }
}
