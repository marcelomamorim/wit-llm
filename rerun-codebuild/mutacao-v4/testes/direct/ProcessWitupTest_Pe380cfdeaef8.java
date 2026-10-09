/* @test
   @summary Verifies that calling toHandle() on a Process instance throws UnsupportedOperationException.
   @run main ProcessWitupTest_Pe380cfdeaef8
 */
import java.lang.Process;
public class ProcessWitupTest_Pe380cfdeaef8 {
    public static void main(String[] args) {
        Process process = new Process() {
            @Override
            public ProcessHandle toHandle() {
                return super.toHandle();
            }
        };
        try {
            process.toHandle();
            throw new AssertionError("Expected UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}
