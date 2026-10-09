/* @test
   @summary Tests the write(byte[] buf, int off, int len) method of ObjectOutputStream for null and index bounds.
   @run main ObjectOutputStreamWitupTest_P0a7c607b70b4
 */
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
public class ObjectOutputStreamWitupTest_P0a7c607b70b4 {
    public static void main(String[] args) throws Exception {
        testWriteNullBuffer();
        testWriteIndexOutOfBounds();
    }
    private static void testWriteNullBuffer() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        try {
            oos.write(null, 0, 0);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
        oos.close();
    }
    private static void testWriteIndexOutOfBounds() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] buffer = {1, 2, 3, 4, 5};
        // off + len > buffer.length
        try {
            oos.write(buffer, 3, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // off < 0
        try {
            oos.write(buffer, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // len < 0
        try {
            oos.write(buffer, 1, -2);
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        // endoff < 0 (simulate by large off and len)
        try {
            oos.write(buffer, 4, 2); // 4 + 2 = 6 > buffer.length
            throw new AssertionError("Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        oos.close();
    }
}
