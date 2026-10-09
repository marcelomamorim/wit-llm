/* @test
   @summary Verifies the behavior of ObjectOutputStream.write(byte[], int, int).
   @run main ObjectOutputStreamWitupTest_P0a7c607b70b4
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
public class ObjectOutputStreamWitupTest_P0a7c607b70b4 {
    public static void main(String[] args) throws IOException {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Test normal write
        oos.write(data, 0, 3);
        byte[] result = baos.toByteArray();
        // Expect the first 3 bytes of data to be written
        for (int i = 0; i < 3; i++) {
            if (result[i] != data[i]) {
                throw new AssertionError("Data mismatch at index " + i);
            }
        }
        // Test NullPointerException when buf is null
        try {
            oos.write(null, 0, 1);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
        // Test IndexOutOfBoundsException for invalid off and len
        try {
            oos.write(data, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative off");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            oos.write(data, 0, -1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative len");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            oos.write(data, 4, 2); // end index exceeds array length
            throw new AssertionError("Expected IndexOutOfBoundsException for end index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        oos.close();
    }
}
