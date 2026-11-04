/*
 * @test
 * @summary Test for ImageStream.put(ImageStream) method, focusing on exception path.
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testPutWithNegativePosition();
        testPutWithValidStream();
    }

    private static void testPutWithNegativePosition() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            buffer.position(-1); // invalid position
            ImageStream stream = new ImageStream(buffer);
            ImageStream target = new ImageStream();
            target.put(stream);
            System.out.println("FAIL: Expected IndexOutOfBoundsException not thrown");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected IndexOutOfBoundsException");
        } catch (Exception e) {
            System.out.println("FAIL: Caught unexpected exception: " + e);
        }
    }

    private static void testPutWithValidStream() {
        try {
            byte[] data = {1, 2, 3, 4, 5};
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.position(2);
            ImageStream stream = new ImageStream(buffer);
            ImageStream target = new ImageStream();
            target.put(stream);
            System.out.println("PASS: put() executed without exception");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
