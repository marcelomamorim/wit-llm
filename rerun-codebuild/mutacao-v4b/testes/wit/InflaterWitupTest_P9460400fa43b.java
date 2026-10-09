/* @test
   @summary Verifica o comportamento do método setInput(ByteBuffer) para entrada nula.
   @run main InflaterWitupTest_P9460400fa43b
 */
import java.util.zip.Inflater;
import java.nio.ByteBuffer;
public class InflaterWitupTest_P9460400fa43b {
    public static void main(String[] args) {
        testSetInputWithNull();
    }
    public static void testSetInputWithNull() {
        Inflater inflater = new Inflater();
        try {
            inflater.setInput((ByteBuffer) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
