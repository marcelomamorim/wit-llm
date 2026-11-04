/* @test    @summary Test Inflater.setInput(ByteBuffer) with non-null ByteBuffer    @run main InflaterWitupTest */

import java.util.zip.Inflater;
import java.nio.ByteBuffer;

public class InflaterWitupTest {

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
