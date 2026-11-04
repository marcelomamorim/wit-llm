/*
 * @test
 * @summary Test ObjectOutputStream(OutputStream) constructor basic behavior
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Test normal construction with ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        // Write some primitive data and an object to verify stream usability
        oos.writeInt(42);
        oos.writeObject("test");
        oos.flush();

        // The underlying ByteArrayOutputStream should have data
        byte[] data = baos.toByteArray();
        if (data.length == 0) {
            throw new AssertionError("No data written to underlying stream");
        }

        oos.close();

        // Test that constructor throws NullPointerException if null OutputStream passed
        boolean npeThrown = false;
        try {
            new ObjectOutputStream(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new AssertionError("Expected NullPointerException when passing null OutputStream");
        }
    }
}
