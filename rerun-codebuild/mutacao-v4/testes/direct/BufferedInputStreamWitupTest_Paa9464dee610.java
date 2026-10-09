/* @test
   @summary Tests the reset() method of BufferedInputStream for correct behavior.
   @run main BufferedInputStreamWitupTest_Paa9464dee610
 */
import java.io.*;
public class BufferedInputStreamWitupTest_Paa9464dee610 {
    public static void main(String[] args) throws Exception {
        byte[] data = {1, 2, 3, 4, 5};
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        BufferedInputStream bis = new BufferedInputStream(byteStream);
        // Mark the current position
        bis.mark(10);
        // Read some bytes
        bis.read();
        bis.read();
        // Reset to the marked position
        bis.reset();
        // Read again from the reset position
        int firstByte = bis.read();
        if (firstByte != data[0]) {
            throw new AssertionError("reset() did not reset to the marked position correctly");
        }
        // Test reset() without mark() set
        ByteArrayInputStream newStream = new ByteArrayInputStream(data);
        BufferedInputStream bis2 = new BufferedInputStream(newStream);
        try {
            bis2.reset();
            throw new AssertionError("Expected IOException when reset() called without mark()");
        } catch (IOException e) {
            // expected
        }
        // Test reset() after close
        ByteArrayInputStream closeStream = new ByteArrayInputStream(data);
        BufferedInputStream bis3 = new BufferedInputStream(closeStream);
        bis3.close();
        try {
            bis3.reset();
            throw new AssertionError("Expected IOException after close()");
        } catch (IOException e) {
            // expected
        }
    }
}
