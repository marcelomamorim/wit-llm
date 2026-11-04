/*
 * @test
 * @summary BufferedReader(Reader, int) must reject non-positive buffer sizes and accept positive ones
 * @run main BufferedReaderWitupTest
 */

import java.io.BufferedReader;
import java.io.StringReader;

public class BufferedReaderWitupTest {

    public static void main(String[] args) throws Exception {
        checkRejects(0);
        checkRejects(-1);
        checkRejects(Integer.MIN_VALUE);

        checkAcceptsAndReads(1);
        checkAcceptsAndReads(8192);
    }

    static void checkRejects(int sz) {
        try {
            new BufferedReader(new StringReader("abc"), sz);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for sz=" + sz);
    }

    static void checkAcceptsAndReads(int sz) throws Exception {
        try (BufferedReader br = new BufferedReader(new StringReader("ab\ncd"), sz)) {
            String first = br.readLine();
            String second = br.readLine();
            String third = br.readLine();
            if (!"ab".equals(first)) {
                throw new RuntimeException("Unexpected first line for sz=" + sz + ": " + first);
            }
            if (!"cd".equals(second)) {
                throw new RuntimeException("Unexpected second line for sz=" + sz + ": " + second);
            }
            if (third != null) {
                throw new RuntimeException("Expected end of stream for sz=" + sz + ", got: " + third);
            }
        }
    }
}
