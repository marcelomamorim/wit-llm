/*
 * @test
 * @summary Test for ensure method in jdk.internal.jimage.ImageStream
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testEnsureNegativeNeeds();
        testEnsureSufficientCapacity();
    }

    private static void testEnsureNegativeNeeds() {
        jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream();
        try {
            stream.ensure(-1);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative needs");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException for negative needs");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testEnsureSufficientCapacity() {
        jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream(10);
        // Fill buffer to position 5
        for (int i = 0; i < 5; i++) {
            stream.buffer.put((byte)i);
        }
        stream.buffer.position(5);
        int currentCapacity = stream.buffer.capacity();
        int needs = currentCapacity + 10;
        try {
            stream.ensure(needs);
            if (stream.buffer.capacity() >= needs) {
                System.out.println("PASS: Buffer resized to accommodate needs");
            } else {
                System.out.println("FAIL: Buffer not resized correctly");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception during ensure: " + t);
        }
    }
}
