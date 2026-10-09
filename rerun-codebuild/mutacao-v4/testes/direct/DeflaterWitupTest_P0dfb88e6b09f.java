/* @test
   @summary Tests the setInput(ByteBuffer) method of java.util.zip.Deflater.
   @run main DeflaterWitupTest_P0dfb88e6b09f
 */
import java.util.zip.Deflater;
import java.nio.ByteBuffer;
public class DeflaterWitupTest_P0dfb88e6b09f {
    public static void main(String[] args) {
        testSetInputWithValidBuffer();
        testSetInputWithNullBuffer();
    }
    private static void testSetInputWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Deflater deflater = new Deflater();
        deflater.setInput(buffer);
        System.out.println("testSetInputWithValidBuffer passed");
    }
    private static void testSetInputWithNullBuffer() {
        try {
            Deflater deflater = new Deflater();
            deflater.setInput(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("testSetInputWithNullBuffer passed");
        }
    }
}
