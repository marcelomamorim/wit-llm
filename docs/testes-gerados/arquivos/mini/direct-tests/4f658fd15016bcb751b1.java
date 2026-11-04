/*
 * @test
 * @summary Test sun.security.util.DerOutputStream.putInteger(Integer) method
 * @run main DerOutputStreamWitupTest
 */

import sun.security.util.DerOutputStream;
import java.io.IOException;

public class DerOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        DerOutputStream dos = new DerOutputStream();

        // Test with zero
        dos.putInteger(Integer.valueOf(0));
        byte[] resultZero = dos.toByteArray();
        if (resultZero.length == 0) {
            throw new RuntimeException("putInteger output is empty for zero");
        }

        // Reset stream
        dos.reset();

        // Test with positive integer
        dos.putInteger(Integer.valueOf(123456));
        byte[] resultPos = dos.toByteArray();
        if (resultPos.length == 0) {
            throw new RuntimeException("putInteger output is empty for positive integer");
        }

        // Reset stream
        dos.reset();

        // Test with negative integer
        dos.putInteger(Integer.valueOf(-123456));
        byte[] resultNeg = dos.toByteArray();
        if (resultNeg.length == 0) {
            throw new RuntimeException("putInteger output is empty for negative integer");
        }

        // No exceptions means pass
        System.out.println("All putInteger(Integer) tests passed.");
    }
}
