/*
 * @test
 * @summary Witup-generated tests for HeaderParser.subsequence argument validation and observable results
 * @modules java.base/sun.net.www
 * @run main HeaderParserWitupTest
 */
import sun.net.www.HeaderParser;

public class HeaderParserWitupTest {
    public static void main(String[] args) {
        HeaderParser hp = new HeaderParser("a=\"1\", b=\"2\"");

        if (hp.subsequence(0, 2) != hp) {
            throw new RuntimeException("subsequence(0, nkeys) should return this");
        }

        expectIAE(hp, -1, 1);
        expectIAE(hp, 1, 1);
        expectIAE(hp, 1, 0);
        expectIAE(hp, 0, 3);
        expectIAE(hp, 2, 3);

        HeaderParser first = hp.subsequence(0, 1);
        if (first == null || first == hp) {
            throw new RuntimeException("subsequence(0, 1) should return a new non-null parser");
        }
        if (first.subsequence(0, 1) != first) {
            throw new RuntimeException("single-key subsequence should return this for (0, 1)");
        }
        expectIAE(first, 0, 2);

        HeaderParser second = hp.subsequence(1, 2);
        if (second == null || second == hp) {
            throw new RuntimeException("subsequence(1, 2) should return a new non-null parser");
        }
        if (second.subsequence(0, 1) != second) {
            throw new RuntimeException("single-key subsequence should return this for (0, 1)");
        }
    }

    static void expectIAE(HeaderParser hp, int start, int end) {
        try {
            hp.subsequence(start, end);
        } catch (IllegalArgumentException ex) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for start=" + start + ", end=" + end);
    }
}
