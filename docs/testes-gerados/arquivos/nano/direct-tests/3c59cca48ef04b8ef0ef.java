/* @test    @summary Test ObjectOutputStream.putFields() method for correct behavior and exception handling    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectOutputStream.PutField;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) {
        testPutFieldsNormal();
        testPutFieldsNotActive();
    }

    private static void testPutFieldsNormal() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            PutField pf = oos.putFields();
            if (pf == null) {
                throw new AssertionError("putFields() returned null");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    private static void testPutFieldsNotActive() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // Manually invoke putFields() outside of writeObject context
            oos.putFields();
            throw new AssertionError("Expected NotActiveException was not thrown");
        } catch (NotActiveException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }
}
