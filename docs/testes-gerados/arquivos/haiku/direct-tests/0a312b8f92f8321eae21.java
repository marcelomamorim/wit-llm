/*
 * @test
 * @summary Verify ObjectOutputStream.writeFields() throws NotActiveException when no PutField is active and the stream remains usable afterwards
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.NotActiveException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteFieldsWithoutPutFieldsThrows();
        testStreamUsableAfterWriteFieldsException();
    }

    static void testWriteFieldsWithoutPutFieldsThrows() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        try {
            oos.writeFields();
            throw new AssertionError("expected NotActiveException");
        } catch (NotActiveException expected) {
            // expected: no current PutField object
        }
        oos.close();
    }

    static void testStreamUsableAfterWriteFieldsException() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        try {
            oos.writeFields();
            throw new AssertionError("expected NotActiveException");
        } catch (NotActiveException expected) {
            // expected
        }
        oos.flush();
        int sizeBefore = bos.size();
        oos.writeInt(42);
        oos.flush();
        int sizeAfter = bos.size();
        if (sizeAfter <= sizeBefore) {
            throw new AssertionError("stream not writable after exception: "
                    + sizeBefore + " -> " + sizeAfter);
        }
        oos.close();
    }
}
