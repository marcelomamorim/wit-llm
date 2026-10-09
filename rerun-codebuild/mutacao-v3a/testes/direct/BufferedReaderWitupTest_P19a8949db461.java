/*
 * @test    @summary Test BufferedReader.mark method for JDK 11 compatibility
 * @run main BufferedReaderWitupTest_P19a8949db461
 */

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderWitupTest_P19a8949db461 {

    public static void main(String[] args) {
        testMarkWithValidLimit();
        testMarkWithNegativeLimit();
        testMarkMultipleTimes();
    }

    static void testMarkWithValidLimit() {
        String data = "Line1\nLine2\nLine3";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            br.mark(10);
            int c;
            StringBuilder sb = new StringBuilder();
            while ((c = br.read()) != -1) {
                sb.append((char)c);
            }
            if (!sb.toString().equals(data)) {
                throw new AssertionError("Content mismatch in testMarkWithValidLimit");
            }
        } catch (IOException e) {
            throw new AssertionError("IOException in testMarkWithValidLimit", e);
        }
    }

    static void testMarkWithNegativeLimit() {
        String data = "Test";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            boolean thrown = false;
            try {
                br.mark(-1);
            } catch (IllegalArgumentException e) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected IllegalArgumentException not thrown in testMarkWithNegativeLimit");
            }
        } catch (IOException e) {
            throw new AssertionError("IOException in testMarkWithNegativeLimit", e);
        }
    }

    static void testMarkMultipleTimes() {
        String data = "ABCDE";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            br.mark(5);
            int c;
            StringBuilder sb = new StringBuilder();
            sb.append((char)br.read()); // 'A'
            br.reset();
            br.mark(5);
            while ((c = br.read()) != -1) {
                sb.append((char)c);
            }
            String expected = "AABCDE";
            if (!sb.toString().equals(expected)) {
                throw new AssertionError("Content mismatch in testMarkMultipleTimes: expected '" + expected + "', got '" + sb.toString() + "'");
            }
        } catch (IOException e) {
            throw new AssertionError("IOException in testMarkMultipleTimes", e);
        }
    }
}
