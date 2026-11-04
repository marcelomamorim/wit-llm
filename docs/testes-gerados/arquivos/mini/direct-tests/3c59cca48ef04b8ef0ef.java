/*
 * @test
 * @summary Test ObjectOutputStream.putFields() behavior including NotActiveException
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectOutputStream;
import java.io.ObjectOutputStream.PutField;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testPutFieldsThrowsNotActiveException();
        testPutFieldsReturnsPutField();
    }

    static void testPutFieldsThrowsNotActiveException() {
        try {
            // Use protected no-arg constructor to create ObjectOutputStream without context
            ObjectOutputStream oos = new ObjectOutputStream() {
                // Override writeObject0 to do nothing to avoid side effects
                @Override
                protected void writeObject0(Object obj, boolean unshared) throws IOException {
                    // no-op
                }
            };
            // Directly call putFields() without writeObject context
            oos.putFields();
            throw new AssertionError("Expected NotActiveException not thrown");
        } catch (NotActiveException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testPutFieldsReturnsPutField() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        // Write an object to establish context
        oos.writeObject("test");

        // Now putFields() should return a non-null PutField instance
        PutField pf = oos.putFields();
        if (pf == null) {
            throw new AssertionError("putFields() returned null PutField");
        }

        // Call putFields() again should return the same instance
        PutField pf2 = oos.putFields();
        if (pf != pf2) {
            throw new AssertionError("putFields() did not return the same PutField instance on repeated calls");
        }

        oos.close();
    }
}
