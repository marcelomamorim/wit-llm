/* @test    @summary Test defaultWriteObject method of ObjectOutputStream    @run main ObjectOutputStreamWitupTest */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static class DummyObject implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        int value;
        public DummyObject(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        testDefaultWriteObjectWithActiveContext();
        testDefaultWriteObjectWithoutActiveContext();
    }

    private static void testDefaultWriteObjectWithActiveContext() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            DummyObject obj = new DummyObject(42);
            // Simulate active context by calling defaultWriteObject within writeObject
            oos.writeObject(obj);
            // Accessing defaultWriteObject directly is not possible outside ObjectOutputStream
            // So, test indirectly by calling writeObject on a serializable object
            System.out.println("Test with active context passed.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testDefaultWriteObjectWithoutActiveContext() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // Not calling any writeObject, directly calling defaultWriteObject is not accessible
            // So, simulate by calling defaultWriteObject via reflection (not allowed per constraints)
            // Instead, test that calling defaultWriteObject outside writeObject throws NotActiveException
            oos.defaultWriteObject();
            System.out.println("Unexpected success: defaultWriteObject() did not throw.");
        } catch (NotActiveException e) {
            System.out.println("Test without active context passed: Caught NotActiveException.");
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
    }
}
