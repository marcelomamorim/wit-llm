/*
 * @test
 * @summary Verifies that ObjectInputStream restores shared and cyclic back-references, which exercises the handle table lookup used by the get(int) method
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjectInputStreamWitupTest {

    static class Holder implements Serializable {
        private static final long serialVersionUID = 1L;
        Object a;
        Object b;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] serialize(Object o) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(o);
        }
        return bos.toByteArray();
    }

    static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return ois.readObject();
        }
    }

    public static void main(String[] args) throws Exception {
        // Shared reference: both fields must point to the same restored instance
        List<String> shared = new ArrayList<>();
        shared.add("x");
        Holder h = new Holder();
        h.a = shared;
        h.b = shared;

        Holder r = (Holder) deserialize(serialize(h));
        check(r.a == r.b, "shared back-reference not restored to same instance");
        check(r.a instanceof List, "restored shared object has wrong type");
        List<?> restored = (List<?>) r.a;
        check(restored.size() == 1, "restored shared list has wrong size");
        check("x".equals(restored.get(0)), "restored shared list has wrong content");

        // Cyclic reference: object refers to itself through a field
        Holder cyc = new Holder();
        cyc.a = cyc;
        cyc.b = null;

        Holder rc = (Holder) deserialize(serialize(cyc));
        check(rc.a == rc, "cyclic back-reference not restored to same instance");
        check(rc.b == null, "null field not preserved");
    }
}
