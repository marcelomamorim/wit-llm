/*
 * @test
 * @summary Verifies ObjectOutputStream.writeChars(String) round-trips characters, including chunk boundaries, and rejects null with NullPointerException
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        roundTrip("");
        roundTrip("a");
        roundTrip("abc");
        roundTrip(build(1023));
        roundTrip(build(1024));
        roundTrip(build(1025));
        roundTrip(build(2500));
        roundTrip("\u00e9\u4e2d\ud83d\ude00x");
        nullStringThrowsNpe();
    }

    static String build(int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append((char) ('a' + (i % 26)));
        }
        return sb.toString();
    }

    static void roundTrip(String s) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeChars(s);
            oos.flush();
        }
        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()))) {
            for (int i = 0; i < s.length(); i++) {
                char c = ois.readChar();
                if (c != s.charAt(i)) {
                    throw new RuntimeException("mismatch at index " + i
                            + ": expected " + (int) s.charAt(i)
                            + " but was " + (int) c);
                }
            }
        }
    }

    static void nullStringThrowsNpe() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        try {
            oos.writeChars(null);
            throw new RuntimeException("expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } finally {
            oos.close();
        }
    }
}
