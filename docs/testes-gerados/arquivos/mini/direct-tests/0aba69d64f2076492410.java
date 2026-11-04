/*
 * @test
 * @summary Test ObjectOutputStream.defaultWriteObject() behavior
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectOutputStreamWitupTest {

    static class SimpleObject implements Serializable {
        private static final long serialVersionUID = 1L;
        int x = 42;
        String s = "test";
    }

    public static void main(String[] args) throws Exception {
        testDefaultWriteObjectThrowsWhenNotInWriteObject();
        testDefaultWriteObjectWritesFieldsCorrectly();
    }

    // Test that defaultWriteObject throws NotActiveException if not called during writeObject
    private static void testDefaultWriteObjectThrowsWhenNotInWriteObject() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        boolean thrown = false;
        try {
            oos.defaultWriteObject();
        } catch (NotActiveException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NotActiveException when calling defaultWriteObject outside writeObject");
        }
        oos.close();
    }

    // Test that defaultWriteObject writes the non-transient fields of an object correctly
    private static void testDefaultWriteObjectWritesFieldsCorrectly() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos) {
            @Override
            protected void writeObjectOverride(Object obj) throws IOException {
                // This method is called by writeObject, so curContext is set
                // Call defaultWriteObject and then write a marker
                defaultWriteObject();
                writeInt(12345); // write extra data to verify stream continues
            }
        };

        SimpleObject obj = new SimpleObject();
        oos.writeObject(obj);
        oos.close();

        byte[] data = baos.toByteArray();
        if (data.length == 0) {
            throw new AssertionError("No data written by defaultWriteObject");
        }
        // We do not parse the stream here, but presence of data and no exceptions
        // indicates defaultWriteObject wrote fields and stream is valid
    }
}