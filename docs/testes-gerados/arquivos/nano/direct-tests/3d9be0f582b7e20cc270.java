/* @test    @summary Test BufferedReader.mark() method for correct behavior and exception handling    @run main BufferedReaderWitupTest */

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderWitupTest {

    public static void main(String[] args) {
        testMarkWithValidLimit();
        testMarkWithNegativeLimit();
        testMarkWithZeroLimit();
    }

    static void testMarkWithValidLimit() {
        String data = "Line1\nLine2\nLine3";
        BufferedReader br = new BufferedReader(new StringReader(data));
        try {
            br.mark(10);
            br.readLine(); // read first line
            br.reset(); // should reset to position before readLine()
            String line = br.readLine();
            assert line.equals("Line1") : "Expected 'Line1', got: " + line;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testMarkWithValidLimit", e);
        }
    }

    static void testMarkWithNegativeLimit() {
        String data = "Test";
        BufferedReader br = new BufferedReader(new StringReader(data));
        boolean exceptionThrown = false;
        try {
            br.mark(-1);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testMarkWithNegativeLimit", e);
        }
        assert exceptionThrown : "Expected IllegalArgumentException for negative readAheadLimit";
    }

    static void testMarkWithZeroLimit() {
        String data = "Sample";
        BufferedReader br = new BufferedReader(new StringReader(data));
        try {
            br.mark(0);
            br.read();
            br.reset();
            String line = br.readLine();
            assert line != null : "Expected to read a line after mark with zero limit";
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testMarkWithZeroLimit", e);
        }
    }
}
