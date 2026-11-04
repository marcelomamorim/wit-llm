/*
 * @test
 * @summary Test for ImageStream(ByteBuffer buffer) constructor, focusing on null buffer case.
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import jdk.internal.jimage.ImageStream;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testNullByteBuffer();
        testValidByteBuffer();
    }

    private static void testNullByteBuffer() {
        try {
            new ImageStream((ByteBuffer) null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testValidByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        try {
            ImageStream is = new ImageStream(buffer);
            if (is != null) {
                System.out.println("PASS: ImageStream created with valid ByteBuffer");
            } else {
                System.out.println("FAIL: ImageStream instance is null");
            }
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }
}
