/* @test    @summary Test BufferedReader.skip method for correct behavior and exception handling    @run main BufferedReaderWitupTest_P818db4e25355 */

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

public class BufferedReaderWitupTest_P818db4e25355 {

    public static void main(String[] args) {
        testSkipNormal();
        testSkipNegative();
        testSkipBeyondEOF();
        testSkipWithLineFeed();
    }

    private static void testSkipNormal() {
        String data = "Line1\nLine2\nLine3";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            long skipped = br.skip(5);
            if (skipped != 5) {
                throw new AssertionError("Expected to skip 5 characters, but skipped " + skipped);
            }
            int c = br.read();
            if (c != 'L') {
                throw new AssertionError("Expected 'L' after skip, but got: " + (char)c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testSkipNegative() {
        String data = "Test";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            br.skip(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative skip")
        } catch (IllegalArgumentException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testSkipBeyondEOF() {
        String data = "abc";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            long skipped = br.skip(100);
            if (skipped != 3) {
                throw new AssertionError("Expected to skip 3 characters (EOF), but skipped " + skipped);
            }
            int c = br.read();
            if (c != -1) {
                throw new AssertionError("Expected end of stream (-1), but got: " + c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testSkipWithLineFeed() {
        String data = "Line1\nLine2";
        try (BufferedReader br = new BufferedReader(new StringReader(data))) {
            long skipped = br.skip(6); // Skip 'Line1\n'
            if (skipped != 6) {
                throw new AssertionError("Expected to skip 6 characters, but skipped " + skipped);
            }
            int c = br.read();
            if (c != 'L') {
                throw new AssertionError("Expected 'L' after skip, but got: " + (char)c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
