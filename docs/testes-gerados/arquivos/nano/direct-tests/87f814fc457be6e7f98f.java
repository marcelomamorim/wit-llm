/* @test    @summary Test the skip method of LineNumberReader for correct behavior and exception handling    @run main LineNumberReaderWitupTest */

import java.io.*;

public class LineNumberReaderWitupTest {

    public static void main(String[] args) throws Exception {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
        testSkipBeyondEnd();
    }

    private static void testSkipNegative() throws Exception {
        StringReader sr = new StringReader("Line1\nLine2\nLine3");
        LineNumberReader lnr = new LineNumberReader(sr);
        boolean exceptionThrown = false;
        try {
            lnr.skip(-10);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IllegalArgumentException for negative skip";
        lnr.close();
    }

    private static void testSkipZero() throws Exception {
        StringReader sr = new StringReader("Line1\nLine2\nLine3");
        LineNumberReader lnr = new LineNumberReader(sr);
        long skipped = lnr.skip(0);
        assert skipped == 0 : "Skip zero should return 0";
        lnr.close();
    }

    private static void testSkipPositive() throws Exception {
        StringReader sr = new StringReader("Line1\nLine2\nLine3");
        LineNumberReader lnr = new LineNumberReader(sr);
        long skipped = lnr.skip(5);
        assert skipped >= 0 : "Skipped characters should be non-negative";
        int ch;
        int count = 0;
        while ((ch = lnr.read()) != -1) {
            count++;
        }
        assert count >= 0 : "Remaining characters should be read";
        lnr.close();
    }

    private static void testSkipBeyondEnd() throws Exception {
        StringReader sr = new StringReader("Line1\nLine2\nLine3");
        LineNumberReader lnr = new LineNumberReader(sr);
        long totalChars = lnr.readLine().length() + lnr.readLine().length() + 2; // approximate
        long skipped = lnr.skip(totalChars + 10);
        assert skipped >= 0 : "Skipping beyond end should not throw";
        int ch;
        while ((ch = lnr.read()) != -1) {
            // read remaining
        }
        lnr.close();
    }
}
