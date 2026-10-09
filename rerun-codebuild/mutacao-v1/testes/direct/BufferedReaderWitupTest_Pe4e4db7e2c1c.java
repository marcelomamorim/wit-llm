/* @test    @summary Test BufferedReader.mark() method for correct behavior and exception handling    @run main BufferedReaderWitupTest_Pe4e4db7e2c1c */

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderWitupTest_Pe4e4db7e2c1c {

    public static void main(String[] args) {
        testMarkWithValidLimit();
        testMarkWithNegativeLimit();
        testMarkWithZeroLimit();
        testMarkWithLargeLimit();
        System.out.println("All tests passed.");
    }

    private static void testMarkWithValidLimit() {
        String data = "Line1\nLine2\nLine3";
        BufferedReader br = new BufferedReader(new StringReader(data));
        try {
            br.mark(10);
            br.readLine(); // read first line
            br.reset(); // should throw IOException, but mark() does not throw
            // Since mark() does not throw, verify that after reset, the position is at start
            br.reset();
            String line = br.readLine();
            if (!"Line1".equals(line)) {
                throw new AssertionError("Expected 'Line1', got: " + line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testMarkWithValidLimit", e);
        }
    }

    private static void testMarkWithNegativeLimit() {
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
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative readAheadLimit");
        }
    }

    private static void testMarkWithZeroLimit() {
        String data = "Test";
        BufferedReader br = new BufferedReader(new StringReader(data));
        try {
            br.mark(0);
            br.read();
            br.reset();
            String line = br.readLine();
            if (!"Test".equals(line)) {
                throw new AssertionError("Expected 'Test', got: " + line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testMarkWithZeroLimit", e);
        }
    }

    private static void testMarkWithLargeLimit() {
        String data = "Line1\nLine2\nLine3";
        BufferedReader br = new BufferedReader(new StringReader(data));
        try {
            br.mark(1000);
            br.readLine(); // read first line
            br.reset();
            String line = br.readLine();
            if (!"Line1".equals(line)) {
                throw new AssertionError("Expected 'Line1' after reset, got: " + line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testMarkWithLargeLimit", e);
        }
    }
}
