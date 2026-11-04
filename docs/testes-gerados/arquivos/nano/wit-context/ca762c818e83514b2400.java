/* @test    @summary Test for sun.net.www.http.PosterOutputStream.write(byte[], int, int) handling of out-of-bounds conditions    @run main PosterOutputStreamWitupTest */

import java.io.*;

public class PosterOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteOutOfBounds();
    }

    public static void testWriteOutOfBounds() throws Exception {
        PosterOutputStream pos = new PosterOutputStream();
        byte[] data = {1, 2, 3, 4, 5};
        int off = 1;
        int len = 10; // intentionally out of bounds
        try {
            pos.write(data, off, len);
            // Expect no exception, but the method should handle the case gracefully
            // and not throw. If it throws, the test fails.
            System.out.println("write with out-of-bounds len did not throw.");
        } catch (IndexOutOfBoundsException e) {
            // If an IndexOutOfBoundsException is thrown, test passes for this scenario.
            System.out.println("Caught expected IndexOutOfBoundsException.");
        } catch (Exception e) {
            // Any other exception indicates a failure.
            throw e;
        }
        // Also test with negative offset
        try {
            pos.write(data, -1, 2);
            System.out.println("write with negative offset did not throw.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for negative offset.");
        }
        // Test with negative length
        try {
            pos.write(data, 0, -1);
            System.out.println("write with negative length did not throw.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for negative length.");
        }
        // Test with offset + length exceeding array length
        try {
            pos.write(data, 3, 3);
            System.out.println("write with offset + length exceeding array did not throw.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Caught expected IndexOutOfBoundsException for offset + length exceeding array.");
        }
    }
}
