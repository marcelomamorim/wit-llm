/*
 * @test
 * @summary ObjectInputStream.readByte returns the signed byte values written by ObjectOutputStream and throws EOFException when no data remains
 * @run main ObjectInputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectInputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testRoundTripSignedBytes();
        testEmptyStreamThrowsEOFException();
        testReadPastEndThrowsEOFException();
    }

    static void testRoundTripSignedBytes() throws IOException {
        byte[] values = {-128, -1, 0, 1, 127};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            for (byte b : values) {
                oos.writeByte(b);
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            for (byte expected : values) {
                byte actual = ois.readByte();
                if (actual != expected) {
                    throw new AssertionError("expected " + expected + " but was " + actual);
                }
            }
        }
    }

    static void testEmptyStreamThrowsEOFException() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            // no data written
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            try {
                ois.readByte();
                throw new AssertionError("EOFException expected");
            } catch (EOFException expected) {
                // expected
            }
        }
    }

    static void testReadPastEndThrowsEOFException() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeByte(42);
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            byte first = ois.readByte();
            if (first != 42) {
                throw new AssertionError("expected 42 but was " + first);
            }
            try {
                ois.readByte();
                throw new AssertionError("EOFException expected after end of data");
            } catch (EOFException expected) {
                // expected
            }
        }
    }
}
