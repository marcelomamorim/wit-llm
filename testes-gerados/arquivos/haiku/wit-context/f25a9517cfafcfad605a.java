/*
 * @test
 * @summary Checks ObjectInputStream.readByte sign handling and EOFException at end of data
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
        testRoundTripSignedValues();
        testReadPastEndThrowsEOF();
    }

    private static void testRoundTripSignedValues() throws IOException {
        int[] written = {0, 1, 0x7F, 0x80, 0xFF};
        byte[] expected = {0, 1, 127, -128, -1};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            for (int v : written) {
                oos.writeByte(v);
            }
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            for (int i = 0; i < expected.length; i++) {
                byte got = ois.readByte();
                if (got != expected[i]) {
                    throw new RuntimeException("readByte at index " + i
                            + " returned " + got + ", expected " + expected[i]);
                }
            }
        }
    }

    private static void testReadPastEndThrowsEOF() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeByte(42);
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            byte first = ois.readByte();
            if (first != 42) {
                throw new RuntimeException("unexpected first byte: " + first);
            }
            try {
                ois.readByte();
                throw new RuntimeException("expected EOFException was not thrown");
            } catch (EOFException expected) {
                // expected behavior
            }
        }
    }
}
