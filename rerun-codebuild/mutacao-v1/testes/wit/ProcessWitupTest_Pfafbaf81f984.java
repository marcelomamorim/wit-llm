/* @test    @summary Test for Process.toHandle() method to verify exception behavior    @run main ProcessWitupTest_Pfafbaf81f984 */

import java.lang.*;

public class ProcessWitupTest_Pfafbaf81f984 {

    public static void main(String[] args) {
        try {
            Process process = new Process();
            process.toHandle();
            throw new AssertionError("Expected UnsupportedOperationException was not thrown.");
        } catch (UnsupportedOperationException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("not supported")) {
                throw new AssertionError("Exception message does not contain expected text.");
            }
        } catch (Throwable t) {
            throw new AssertionError("Unexpected exception type: " + t, t);
        }
    }
}
