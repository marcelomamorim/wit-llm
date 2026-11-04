/*
 * @test
 * @summary Test ObjectOutputStream.writeFields() behavior including NotActiveException
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteFieldsThrowsNotActiveException();
    }

    private static void testWriteFieldsThrowsNotActiveException() throws Exception {
        // Create ObjectOutputStream with a ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        // The field curPut is private and no public API to set it;
        // writeFields() throws NotActiveException if curPut == null.
        // Immediately after construction, curPut is null, so writeFields() should throw.

        boolean thrown = false;
        try {
            oos.writeFields();
        } catch (NotActiveException e) {
            thrown = true;
        }

        if (!thrown) {
            throw new AssertionError("Expected NotActiveException was not thrown by writeFields()");
        }

        oos.close();
    }
}
