/*
 * @test
 * @summary Verify ObjectOutputStream.reset() writes TC_RESET on an idle stream and throws IOException("stream active") when called while the stream is serializing an object
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectOutputStreamWitupTest {

    private static final int TC_RESET = 0x79;

    static class ResetInsideWriteObject implements Serializable {
        private static final long serialVersionUID = 1L;

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.reset();
        }
    }

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.reset();
        oos.flush();
        byte[] data = bos.toByteArray();
        if (data.length == 0 || (data[data.length - 1] & 0xFF) != TC_RESET) {
            throw new RuntimeException("reset() on idle stream did not write TC_RESET");
        }

        oos.writeObject("ok");
        oos.flush();

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        ObjectOutputStream oos2 = new ObjectOutputStream(bos2);
        try {
            oos2.writeObject(new ResetInsideWriteObject());
            throw new RuntimeException("expected IOException from reset() during active serialization");
        } catch (IOException e) {
            if (!"stream active".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage(), e);
            }
        }
    }
}
