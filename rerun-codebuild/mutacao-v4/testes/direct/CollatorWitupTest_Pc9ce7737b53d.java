/* @test
   @summary Tests the setStrength method of java.text.Collator.
   @run main CollatorWitupTest_Pc9ce7737b53d
 */
import java.text.Collator;
public class CollatorWitupTest_Pc9ce7737b53d {
    public static void main(String[] args) {
        Collator collator = Collator.getInstance();
        int[] validValues = {Collator.PRIMARY, Collator.SECONDARY, Collator.TERTIARY, Collator.IDENTICAL};
        for (int value : validValues) {
            collator.setStrength(value);
            // No exception expected for valid values
        }
        // Test invalid values
        int[] invalidValues = {0, -1, 5, Integer.MAX_VALUE};
        for (int invalid : invalidValues) {
            try {
                collator.setStrength(invalid);
                throw new AssertionError("Expected IllegalArgumentException for value: " + invalid);
            } catch (IllegalArgumentException e) {
                // expected
            }
        }
    }
}
