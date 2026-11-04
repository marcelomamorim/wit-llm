/*
 * @test
 * @summary Test ObjectOutputStream.putFields() behavior including NotActiveException
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ObjectOutputStream;
import java.io.NotActiveException;
import java.io.IOException;
import java.io.OutputStream;

public class ObjectOutputStreamWitupTest {

    static class DummyOutputStream extends OutputStream {
        public void write(int b) throws IOException {
            // no-op
        }
    }

    public static void main(String[] args) throws Exception {
        testPutFieldsThrowsNotActiveException();
        testPutFieldsReturnsPutFieldInstance();
    }

    private static void testPutFieldsThrowsNotActiveException() throws Exception {
        // Use protected constructor to create ObjectOutputStream without initialization
        ObjectOutputStream oos = new ObjectOutputStream() {
            // no override
        };

        try {
            oos.putFields();
            throw new RuntimeException("Expected NotActiveException not thrown");
        } catch (NotActiveException e) {
            // expected
        }
    }

    private static void testPutFieldsReturnsPutFieldInstance() throws Exception {
        // Create ObjectOutputStream normally
        ObjectOutputStream oos = new ObjectOutputStream(new DummyOutputStream());

        // Write an object to activate context
        oos.writeObject("test");

        // Now putFields() should return a non-null PutField instance
        ObjectOutputStream.PutField pf = oos.putFields();
        if (pf == null) {
            throw new RuntimeException("putFields() returned null PutField instance");
        }
    }
}
