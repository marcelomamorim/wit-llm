/* @test    @summary Test BufferedReader.mark with valid and invalid readAheadLimit values    @run main BufferedReaderWitupTest_P1ef18cec86cc */

import java.io.BufferedReader;
import java.io.StringReader;

public class BufferedReaderWitupTest_P1ef18cec86cc {

    public static void main(String[] args) throws Exception {
        testMarkWithValidLimit();
        testMarkWithNegativeLimit();
    }

    private static void testMarkWithValidLimit() throws Exception {
        StringReader sr = new StringReader("line1\nline2");
        BufferedReader br = new BufferedReader(sr);
        br.mark(10);
        br.reset(); // Should not throw
        System.out.println("testMarkWithValidLimit passed");
    }

    private static void testMarkWithNegativeLimit() {
        StringReader sr = new StringReader("line1\nline2");
        BufferedReader br = new BufferedReader(sr);
        try {
            br.mark(-1);
            // If no exception, fail
            System.err.println("testMarkWithNegativeLimit failed: no exception thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("testMarkWithNegativeLimit passed");
        } catch (Exception e) {
            System.err.println("testMarkWithNegativeLimit failed: wrong exception: " + e);
        }
    }
}
