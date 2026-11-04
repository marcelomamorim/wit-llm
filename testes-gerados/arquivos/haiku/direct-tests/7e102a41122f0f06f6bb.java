/*
 * @test
 * @summary ObjectInputStream.readBoolean returns values written by ObjectOutputStream and throws EOFException at end of stream
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testRoundTripSequence();
        testEofAfterHeaderOnly();
        testEofAfterConsumingAllBooleans();
    }

    static void testRoundTripSequence() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeBoolean(true);
            oos.writeBoolean(false);
            oos.writeBoolean(true);
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            check(ois.readBoolean() == true, "first value should be true");
            check(ois.readBoolean() == false, "second value should be false");
            check(ois.readBoolean() == true, "third value should be true");
        }
    }

    static void testEofAfterHeaderOnly() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            // header only, no boolean data
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            try {
                ois.readBoolean();
                throw new RuntimeException("expected EOFException");
            } catch (EOFException expected) {
                // expected
            }
        }
    }

    static void testEofAfterConsumingAllBooleans() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeBoolean(false);
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            check(ois.readBoolean() == false, "value should be false");
            try {
                ois.readBoolean();
                throw new RuntimeException("expected EOFException");
            } catch (EOFException expected) {
                // expected
            }
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
