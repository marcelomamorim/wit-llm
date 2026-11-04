/*
 * @test
 * @summary Test for jdk.internal.jimage.ImageStream.skip(int) method.
 * @run main ImageStreamWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ImageStreamWitupTest {

    public static void main(String[] args) {
        testSkipNegative();
        testSkipPositive();
        testSkipZero();
    }

    private static void testSkipNegative() {
        try {
            ByteBuffer buf = ByteBuffer.allocate(10);
            buf.order(ByteOrder.nativeOrder());
            jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream(buf);
            stream.skip(-1);
            System.out.println("testSkipNegative: FAILED (no exception)");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("testSkipNegative: PASSED");
        } catch (Throwable t) {
            System.out.println("testSkipNegative: FAILED (unexpected exception: " + t + ")");
        }
    }

    private static void testSkipPositive() {
        try {
            ByteBuffer buf = ByteBuffer.allocate(10);
            buf.order(ByteOrder.nativeOrder());
            buf.put(new byte[]{1,2,3,4,5,6,7,8,9,10});
            buf.flip();
            jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream(buf);
            stream.skip(3);
            int position = buf.position();
            if (position == 3) {
                System.out.println("testSkipPositive: PASSED");
            } else {
                System.out.println("testSkipPositive: FAILED (position = " + position + ")");
            }
        } catch (Throwable t) {
            System.out.println("testSkipPositive: FAILED (exception: " + t + ")");
        }
    }

    private static void testSkipZero() {
        try {
            ByteBuffer buf = ByteBuffer.allocate(10);
            buf.order(ByteOrder.nativeOrder());
            buf.put(new byte[]{1,2,3});
            buf.flip();
            jdk.internal.jimage.ImageStream stream = new jdk.internal.jimage.ImageStream(buf);
            stream.skip(0);
            int position = buf.position();
            if (position == 0) {
                System.out.println("testSkipZero: PASSED");
            } else {
                System.out.println("testSkipZero: FAILED (position = " + position + ")");
            }
        } catch (Throwable t) {
            System.out.println("testSkipZero: FAILED (exception: " + t + ")");
        }
    }
}
