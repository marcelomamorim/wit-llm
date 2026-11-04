/*
 * @test
 * @summary createFilter(String) rejects null patterns and applies the pattern when deserializing
 * @run main ObjectInputFilterWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class ObjectInputFilterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullPatternThrowsNPE();
        testNonNullFilterIsReturned();
        testAllowedPatternAcceptsClass();
        testRejectedPatternRejectsClass();
    }

    static void testNullPatternThrowsNPE() {
        try {
            ObjectInputFilter.createFilter(null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null pattern");
    }

    static void testNonNullFilterIsReturned() {
        ObjectInputFilter f = ObjectInputFilter.createFilter("java.lang.Integer");
        if (f == null) {
            throw new RuntimeException("createFilter returned null");
        }
    }

    static void testAllowedPatternAcceptsClass() throws Exception {
        byte[] data = serializeInteger();
        ObjectInputFilter f = ObjectInputFilter.createFilter("java.lang.Integer;java.lang.Number;!*");
        Object o = read(data, f);
        if (!Integer.valueOf(42).equals(o)) {
            throw new RuntimeException("Expected 42 but got " + o);
        }
    }

    static void testRejectedPatternRejectsClass() throws Exception {
        byte[] data = serializeInteger();
        ObjectInputFilter f = ObjectInputFilter.createFilter("!java.lang.Integer");
        try {
            read(data, f);
        } catch (InvalidClassException expected) {
            return;
        }
        throw new RuntimeException("Expected InvalidClassException for rejected class");
    }

    static byte[] serializeInteger() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(Integer.valueOf(42));
        }
        return bos.toByteArray();
    }

    static Object read(byte[] data, ObjectInputFilter filter)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            ois.setObjectInputFilter(filter);
            return ois.readObject();
        }
    }
}
