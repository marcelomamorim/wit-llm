/*
 * @test
 * @summary Test Process.info() method throws UnsupportedOperationException as expected
 * @run main ProcessWitupTest
 */

public class ProcessWitupTest {
    public static void main(String[] args) {
        Process p = new Process() {
            @Override
            public ProcessHandle toHandle() {
                throw new UnsupportedOperationException();
            }
        };

        boolean threw = false;
        try {
            p.info();
        } catch (UnsupportedOperationException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected UnsupportedOperationException was not thrown by info()");
        }
    }
}
