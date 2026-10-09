/* @test
   @summary Test the static factory method ValueRange.of with valid parameters.
   @run main ValueRangeWitupTest_Pb9555b91e70c
 */
import java.time.temporal.ValueRange;
public class ValueRangeWitupTest_Pb9555b91e70c {
    public static void main(String[] args) {
        ValueRange vr = ValueRange.of(1, 2, 3, 4);
        if (vr == null) {
            throw new AssertionError("ValueRange.of returned null");
        }
        // Additional checks can be added here if public methods are available
    }
}
