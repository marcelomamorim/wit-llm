/* @test    @summary Test BufferedReader.mark with valid and invalid readAheadLimit values    @run main BufferedReaderWitupTest_P19a8949db461 */

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderWitupTest_P19a8949db461 {

    public static void main(String[] args) throws IOException {
        testMarkWithNegativeLimit();
        testMarkWithZeroLimit();
        testMarkWithPositiveLimit();
    }

    private static void testMarkWithNegativeLimit() {
        BufferedReader br = new BufferedReader(new StringReader("Test"));
        try {
            br.mark(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative limit");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative limit");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }

    private static void testMarkWithZeroLimit() {
        BufferedReader br = new BufferedReader(new StringReader("Test"));
        try {
            br.mark(0);
            System.out.println("PASS: mark(0) executed without exception");
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: Unexpected IllegalArgumentException for zero limit");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }

    private static void testMarkWithPositiveLimit() {
        BufferedReader br = new BufferedReader(new StringReader("Test"));
        try {
            br.mark(10);
            System.out.println("PASS: mark(10) executed without exception");
        } catch (IllegalArgumentException e) {
            System.out.println("FAIL: Unexpected IllegalArgumentException for positive limit");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }
}
