/*
 * @test
 * @summary Verify ObjectOutputStream.writeChars(String) produces the same bytes as per-char writeChar calls and rejects null
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        checkEquivalent("");
        checkEquivalent("A");
        checkEquivalent("AB\u00e9\u20ac");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 2500; i++) {
            sb.append((char) ('a' + i % 26));
        }
        checkEquivalent(sb.toString());

        checkNullThrows();
    }

    static void checkEquivalent(String s) throws IOException {
        byte[] viaString = writeWithWriteChars(s);
        byte[] viaChars = writeWithWriteChar(s);
        if (!Arrays.equals(viaString, viaChars)) {
            throw new RuntimeException("writeChars output differs from writeChar output for length " + s.length());
        }
    }

    static byte[] writeWithWriteChars(String s) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeChars(s);
        oos.flush();
        return baos.toByteArray();
    }

    static byte[] writeWithWriteChar(String s) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        for (int i = 0; i < s.length(); i++) {
            oos.writeChar(s.charAt(i));
        }
        oos.flush();
        return baos.toByteArray();
    }

    static void checkNullThrows() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());
        try {
            oos.writeChars(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null string");
    }
}
