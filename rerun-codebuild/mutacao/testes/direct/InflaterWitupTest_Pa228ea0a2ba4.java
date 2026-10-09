/* @test    @summary Test Inflater.setInput(ByteBuffer) with non-null ByteBuffer    @run main InflaterWitupTest_Pa228ea0a2ba4 */

import java.util.zip.Inflater;
import java.nio.ByteBuffer;

public class InflaterWitupTest_Pa228ea0a2ba4 {

    public static void main(String[] args) {
        testSetInputWithValidByteBuffer();
        testSetInputWithNullByteBuffer();
    }

    private static void testSetInputWithValidByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Inflater inflater = new Inflater();
        inflater.setInput(buffer);
        System.out.println("testSetInputWithValidByteBuffer passed");
    }

    private static void testSetInputWithNullByteBuffer() {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(null);
            System.out.println("testSetInputWithNullByteBuffer failed: no exception thrown");
        } catch (NullPointerException e) {
            System.out.println("testSetInputWithNullByteBuffer passed");
        }
    }
}
