/* @test    @summary Test for DecimalFormat.setRoundingMode handling null argument    @run main DecimalFormatWitupTest_P172ea3d30b5f */

import java.text.DecimalFormat;
import java.math.RoundingMode;

public class DecimalFormatWitupTest_P172ea3d30b5f {

    public static void main(String[] args) {
        testSetRoundingModeNull();
    }

    public static void testSetRoundingModeNull() {
        DecimalFormat df = new DecimalFormat();
        try {
            df.setRoundingMode(null);
            // Se não lançar exceção, o teste falha
            throw new AssertionError("Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            // Teste passou, exceção esperada
        }
    }
}
