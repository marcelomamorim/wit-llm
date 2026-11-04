/*
 * @test
 * @summary Test jdk.internal.jimage.ImageStream.skip(int) for negative input exception and normal behavior
 * @run main ImageStreamWitupTest
 */

import jdk.internal.jimage.ImageStream;
import java.nio.ByteOrder;

public class ImageStreamWitupTest {
    public static void main(String[] args) {
        testSkipNegativeThrows();
        testSkipPositiveAdvancesPosition();
    }

    private static void testSkipNegativeThrows() {
        ImageStream stream = new ImageStream(10, ByteOrder.nativeOrder());
        try {
            stream.skip(-1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for negative skip value");
        } catch (IndexOutOfBoundsException e) {
            String msg = e.getMessage();
            if (!msg.contains("skip value = -1")) {
                throw new RuntimeException("Exception message mismatch: " + msg);
            }
        }
    }

    private static void testSkipPositiveAdvancesPosition() {
        byte[] data = new byte[10];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        ImageStream stream = new ImageStream(data, ByteOrder.nativeOrder());
        int initialPos = stream.buffer.position();
        stream.skip(5);
        int newPos = stream.buffer.position();
        if (newPos != initialPos + 5) {
            throw new RuntimeException("Position not advanced correctly by skip: expected " + (initialPos + 5) + " but was " + newPos);
        }
    }
}
