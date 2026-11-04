/*
 * @test
 * @summary BufferedReader(Reader, int) rejects non-positive sizes and buffers reads correctly with small buffers
 * @run main BufferedReaderWitupTest
 */

public class BufferedReaderWitupTest {

    public static void main(String[] args) throws Exception {
        checkInvalidSize(0);
        checkInvalidSize(-1);
        checkSmallBufferRead();
        checkSmallBufferReadLine();
    }

    static void checkInvalidSize(int sz) {
        try {
            new BufferedReader(new StringReader("x"), sz);
            throw new RuntimeException("Expected IllegalArgumentException for sz=" + sz);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void checkSmallBufferRead() throws Exception {
        BufferedReader br = new BufferedReader(new StringReader("abc"), 1);
        if (br.read() != 'a') throw new RuntimeException("expected 'a'");
        if (br.read() != 'b') throw new RuntimeException("expected 'b'");
        if (br.read() != 'c') throw new RuntimeException("expected 'c'");
        if (br.read() != -1) throw new RuntimeException("expected EOF");
    }

    static void checkSmallBufferReadLine() throws Exception {
        BufferedReader br = new BufferedReader(new StringReader("ab\ncd"), 1);
        String first = br.readLine();
        if (!"ab".equals(first)) throw new RuntimeException("expected 'ab' but got " + first);
        String second = br.readLine();
        if (!"cd".equals(second)) throw new RuntimeException("expected 'cd' but got " + second);
        if (br.readLine() != null) throw new RuntimeException("expected null at EOF");
    }
}
