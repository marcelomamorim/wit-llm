/* @test
   @summary Verifica comportamento do método setCurrency com entrada nula.
   @run main DecimalFormatSymbolsWitupTest_P1bfeeb788039
 */
import java.util.Currency;
import java.text.DecimalFormatSymbols;
public class DecimalFormatSymbolsWitupTest_P1bfeeb788039 {
    public static void main(String[] args) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        try {
            dfs.setCurrency(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
