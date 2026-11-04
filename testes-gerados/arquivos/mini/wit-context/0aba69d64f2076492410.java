/*
 * @test
 * @summary Test ObjectOutputStream.defaultWriteObject() behavior and exceptions
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {
    public static void main(String[] args) throws Exception {
        testDefaultWriteObjectThrowsNotActiveException();
        testDefaultWriteObjectNormalFlow();
    }

    static void testDefaultWriteObjectThrowsNotActiveException() {
        // Create ObjectOutputStream with no active serialization context
        // defaultWriteObject() should throw NotActiveException when curContext == null
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos) {
                // Override to simulate curContext == null by not calling writeObject
                // and calling defaultWriteObject directly
                // We rely on the fact that defaultWriteObject throws NotActiveException if not in writeObject call
            };
            oos.defaultWriteObject();
            throw new AssertionError("Expected NotActiveException not thrown");
        } catch (NotActiveException e) {
            // Expected exception
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }

    static void testDefaultWriteObjectNormalFlow() throws IOException {
        // Test normal flow: writeObject calls defaultWriteObject internally
        // We test that defaultWriteObject does not throw and writes data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        // Write a simple serializable object that calls defaultWriteObject internally
        // Use a simple serializable class with no fields
        SimpleSerializable obj = new SimpleSerializable();
        oos.writeObject(obj);
        oos.flush();
        oos.close();

        byte[] data = baos.toByteArray();
        if (data.length == 0) {
            throw new AssertionError("No data written by defaultWriteObject");
        }
    }

    static class SimpleSerializable implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        private void writeObject(java.io.ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
        }
    }
}
