/* @test
   @summary Verifica o comportamento do método setRoundingMode com entrada nula.
   @run main DecimalFormatWitupTest_Pac9e55454977
 */
import java.text.DecimalFormat;
import java.math.RoundingMode;
public class DecimalFormatWitupTest_Pac9e55454977 {
    public static void main(String[] args) {
        testSetRoundingModeNull();
    }
    private static void testSetRoundingModeNull() {
        DecimalFormat df = new DecimalFormat();
        try {
            df.setRoundingMode(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
