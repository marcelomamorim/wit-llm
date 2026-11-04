/*
 * @test
 * @summary ObjectOutputStream.writeUTF(String) output must round-trip through ObjectInputStream.readUTF()
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        check("");
        check("abc");
        check("h\u00e9llo \u20ac");
        check("a\u0000b");
        check("\ud83d\ude00");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append((char) ('a' + (i % 26)));
        }
        check(sb.toString());

        checkSequence(new String[] { "first", "", "third \u00e7" });
    }

    static void check(String s) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeUTF(s);
        }
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
        String r = ois.readUTF();
        if (!s.equals(r)) {
            throw new RuntimeException("round trip mismatch: expected [" + s
                    + "] but got [" + r + "]");
        }
    }

    static void checkSequence(String[] values) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            for (String v : values) {
                oos.writeUTF(v);
            }
        }
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
        for (String v : values) {
            String r = ois.readUTF();
            if (!v.equals(r)) {
                throw new RuntimeException("sequence mismatch: expected [" + v
                        + "] but got [" + r + "]");
            }
        }
    }
}
