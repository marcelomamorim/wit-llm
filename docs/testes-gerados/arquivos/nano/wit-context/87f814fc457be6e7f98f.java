/*
 * @test
 * @summary Test for LineNumberReader.skip method, focusing on exception path for negative input.
 * @run main LineNumberReaderWitupTest
 */

import java.io.StringReader;
import java.io.IOException;
import java.io.LineNumberReader;

public class LineNumberReaderWitupTest {

    public static void main(String[] args) {
        testSkipNegative();
        testSkipPositive();
    }

    private static void testSkipNegative() {
        StringReader sr = new StringReader("test");
        LineNumberReader lnr = new LineNumberReader(sr);
        try {
            lnr.skip(-1);
            System.err.println("Failed: Expected IllegalArgumentException for negative skip");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Passed: Caught expected IllegalArgumentException for negative skip");
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e);
            System.exit(1);
        }
    }

    private static void testSkipPositive() {
        StringReader sr = new StringReader("line1\nline2\nline3");
        LineNumberReader lnr = new LineNumberReader(sr);
        try {
            long skipped = lnr.skip(5);
            if (skipped >= 0) {
                System.out.println("Passed: skip(5) returned: " + skipped);
            } else {
                System.err.println("Failed: skip(5) returned negative value");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Failed: Unexpected IOException: " + e);
            System.exit(1);
        }
    }
}
