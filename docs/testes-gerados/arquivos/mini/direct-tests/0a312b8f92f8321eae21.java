/*
 * @test
 * @summary Test ObjectOutputStream.writeFields() behavior
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectOutputStream;
import java.io.ObjectOutputStream.PutField;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteFieldsWithoutPutField();
        testWriteFieldsWithPutField();
    }

    static void testWriteFieldsWithoutPutField() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // forcibly set curPut to null by reflection is not allowed,
            // so just call writeFields() immediately after construction
            // which should throw NotActiveException because no PutField set
            oos.writeFields();
            throw new AssertionError("Expected NotActiveException not thrown");
        } catch (NotActiveException e) {
            // expected
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    static void testWriteFieldsWithPutField() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        // Obtain PutField and set a field
        PutField pf = oos.putFields();
        pf.put("a", 123); // put an int field named "a"

        // writeFields should succeed without exception
        oos.writeFields();

        // write some more data to verify stream is still usable
        oos.writeInt(42);
        oos.close();

        byte[] data = baos.toByteArray();
        if (data.length == 0) {
            throw new AssertionError("No data written to stream");
        }
    }
}
