/*
 * @test
 * @summary Round-trip and UTF length boundary checks for ObjectOutputStream.writeUTF(String)
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        roundTrip("");
        roundTrip("abc");
        roundTrip("h\u00e9llo \u4e2d\u6587");
        roundTrip(repeat('a', 65535));
        expectOverflow(repeat('a', 65536));
        System.out.println("PASS");
    }

    private static String repeat(char c, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static void roundTrip(String s) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeUTF(s);
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
        String r = ois.readUTF();
        if (!s.equals(r)) {
            throw new RuntimeException("round-trip mismatch for length " + s.length());
        }
    }

    private static void expectOverflow(String s) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        boolean thrown = false;
        try {
            oos.writeUTF(s);
            oos.flush();
        } catch (IOException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("expected IOException for encoded length > 65535");
        }
    }
}
