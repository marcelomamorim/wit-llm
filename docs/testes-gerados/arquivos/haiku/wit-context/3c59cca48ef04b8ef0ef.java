/*
 * @test
 * @summary Verify that ObjectOutputStream.putFields() throws NotActiveException when called outside writeObject
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.NotActiveException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new ByteArrayOutputStream());

        boolean thrown = false;
        try {
            oos.putFields();
        } catch (NotActiveException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("putFields() did not throw NotActiveException outside writeObject");
        }

        thrown = false;
        try {
            oos.putFields();
        } catch (NotActiveException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("second putFields() call did not throw NotActiveException");
        }
    }
}
