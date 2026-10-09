/*
 * @test
 * @summary Tests for BufferedReader.skip method, focusing on negative input handling.
 * @run main BufferedReaderWitupTest_Pe48407034426
 */

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderWitupTest_Pe48407034426 {

    public static void main(String[] args) {
        testSkipNegative();
        testSkipZero();
        testSkipPositive();
    }

    private static void testSkipNegative() {
        StringReader sr = new StringReader("abc");
        BufferedReader br = new BufferedReader(sr);
        try {
            br.skip(-1);
            System.out.println("FAIL: Expected IllegalArgumentException for negative skip");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative skip");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }

    private static void testSkipZero() {
        StringReader sr = new StringReader("abc");
        BufferedReader br = new BufferedReader(sr);
        try {
            long skipped = br.skip(0);
            if (skipped == 0) {
                System.out.println("PASS: skip(0) returns 0 as expected");
            } else {
                System.out.println("FAIL: skip(0) returned " + skipped);
            }
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }

    private static void testSkipPositive() {
        StringReader sr = new StringReader("abc\ndef");
        BufferedReader br = new BufferedReader(sr);
        try {
            long skipped = br.skip(2);
            if (skipped == 2) {
                int ch1 = br.read();
                int ch2 = br.read();
                if (ch1 == 'c' && ch2 == '\n') {
                    System.out.println("PASS: skip(2) skips correctly and subsequent reads are as expected");
                } else {
                    System.out.println("FAIL: Unexpected characters after skip: " + (char)ch1 + ", " + (char)ch2);
                }
            } else {
                System.out.println("FAIL: skip(2) returned " + skipped);
            }
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }
}
