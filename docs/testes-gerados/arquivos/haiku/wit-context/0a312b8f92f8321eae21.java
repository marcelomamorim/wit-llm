/*
 * @test
 * @summary Verifies that ObjectOutputStream.writeFields() throws NotActiveException when no PutField is active, and that the stream remains usable afterwards
 * @run main ObjectOutputStreamWitupTest
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        checkNotActive(oos);
        checkNotActive(oos);

        oos.writeInt(42);
        oos.flush();
        if (bos.size() == 0) {
            throw new AssertionError("stream produced no output");
        }
        oos.close();
    }

    private static void checkNotActive(ObjectOutputStream oos) throws IOException {
        try {
            oos.writeFields();
            throw new AssertionError("expected NotActiveException");
        } catch (NotActiveException e) {
            if (!"no current PutField object".equals(e.getMessage())) {
                throw new AssertionError("unexpected message: " + e.getMessage());
            }
        }
    }
}
