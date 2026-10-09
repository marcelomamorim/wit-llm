/* @test
   @summary Testa o método setStrength de java.text.Collator para entradas válidas e inválidas.
   @run main CollatorWitupTest_Pc9ce7737b53d
 */
import java.text.Collator;
public class CollatorWitupTest_Pc9ce7737b53d {
    public static void main(String[] args) {
        testSetStrengthValidValues();
        testSetStrengthInvalidValues();
    }

    private static void testSetStrengthValidValues() {
        Collator collator = Collator.getInstance();
        try {
            collator.setStrength(Collator.PRIMARY);
            collator.setStrength(Collator.SECONDARY);
            collator.setStrength(Collator.TERTIARY);
            collator.setStrength(Collator.IDENTICAL);
        } catch (Exception e) {
            throw new AssertionError("Valid strength values should not throw exception", e);
        }
    }

    private static void testSetStrengthInvalidValues() {
        Collator collator = Collator.getInstance();
        int[] invalidValues = { -1, 4, 100, Integer.MAX_VALUE };
        for (int invalid : invalidValues) {
            try {
                collator.setStrength(invalid);
                throw new AssertionError("Expected IllegalArgumentException for value: " + invalid);
            } catch (IllegalArgumentException e) {
                // expected
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception for value: " + invalid, e);
            }
        }
    }
}
