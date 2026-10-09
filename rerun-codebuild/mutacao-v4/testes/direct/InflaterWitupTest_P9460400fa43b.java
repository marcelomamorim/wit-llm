/* @test
   @summary Tests the setInput(ByteBuffer) method of java.util.zip.Inflater.
   @run main InflaterWitupTest_P9460400fa43b
 */
import java.util.zip.Inflater;
import java.nio.ByteBuffer;
public class InflaterWitupTest_P9460400fa43b {
    public static void main(String[] args) {
        testSetInputWithValidBuffer();
        testSetInputWithNullBuffer();
    }

    static void testSetInputWithValidBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        Inflater inflater = new Inflater();
        inflater.setInput(buffer);
        // No exception expected, test passes if no exception thrown
    }

    static void testSetInputWithNullBuffer() {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(null);
            throw new AssertionError("NullPointerException expected");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
