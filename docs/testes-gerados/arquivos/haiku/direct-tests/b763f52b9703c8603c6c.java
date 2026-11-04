/*
 * @test
 * @summary ObjectOutputStream.reset writes TC_RESET to the stream and rejects reset while an object is being written
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectOutputStreamWitupTest {

    static class Holder implements Serializable {
        private static final long serialVersionUID = 1L;

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.reset();
        }
    }

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.reset();
        oos.flush();
        byte[] b = baos.toByteArray();
        if (b.length != 5
                || (b[0] & 0xff) != 0xAC
                || (b[1] & 0xff) != 0xED
                || b[2] != 0
                || b[3] != 5
                || b[4] != 0x79) {
            throw new RuntimeException("unexpected bytes after reset: length=" + b.length);
        }

        ObjectOutputStream oos2 = new ObjectOutputStream(new ByteArrayOutputStream());
        try {
            oos2.writeObject(new Holder());
            throw new RuntimeException("expected IOException from reset during writeObject");
        } catch (IOException e) {
            if (!"stream active".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
