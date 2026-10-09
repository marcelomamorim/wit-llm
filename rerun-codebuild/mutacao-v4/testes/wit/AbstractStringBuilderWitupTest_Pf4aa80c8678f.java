/* @test
   @summary Tests offsetByCodePoints for IndexOutOfBoundsException.
   @run main AbstractStringBuilderWitupTest_Pf4aa80c8678f
 */
import java.lang.IndexOutOfBoundsException;
public class AbstractStringBuilderWitupTest_Pf4aa80c8678f {
    public static void main(String[] args) {
        testOffsetByCodePointsOutOfBounds();
    }
    private static void testOffsetByCodePointsOutOfBounds() {
        java.lang.AbstractStringBuilder sb = new java.lang.StringBuilder();
        int length = sb.length();
        try {
            sb.offsetByCodePoints(-1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.offsetByCodePoints(length + 1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.offsetByCodePoints(0, 0); // valid case, should not throw
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Did not expect exception for index 0");
        }
        try {
            sb.offsetByCodePoints(length, 0); // valid case, should not throw
        } catch (IndexOutOfBoundsException e) {
            throw new AssertionError("Did not expect exception for index == length");
        }
    }
}
