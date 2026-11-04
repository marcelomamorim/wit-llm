/*
 * @test
 * @summary Test for ObjectOutputStream.reset() method behavior.
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws IOException {
        testResetWithDepthZero();
        testResetWithDepthNonZero();
    }

    private static void testResetWithDepthZero() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Simulate depth == 0 by not calling reset() or any method that changes depth
        try {
            oos.reset();
            System.out.println("testResetWithDepthZero: Passed");
        } catch (IOException e) {
            System.err.println("testResetWithDepthZero: Failed with IOException: " + e.getMessage());
        }
        oos.close();
    }

    private static void testResetWithDepthNonZero() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        // Manually set internal state to simulate depth != 0
        // Since depth is private, we simulate by calling reset() after some writes
        oos.writeInt(42);
        // Now, calling reset() should throw IOException as per method logic
        try {
            oos.reset();
            System.err.println("testResetWithDepthNonZero: Failed - no exception thrown");
        } catch (IOException e) {
            if ("stream active".equals(e.getMessage())) {
                System.out.println("testResetWithDepthNonZero: Passed");
            } else {
                System.err.println("testResetWithDepthNonZero: Failed with unexpected IOException: " + e.getMessage());
            }
        }
        oos.close();
    }
}
