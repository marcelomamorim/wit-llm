/*
 * @test
 * @summary Public-contract checks for HeaderParser.subsequence argument validation
 * @modules java.base/sun.net.www
 * @run main HeaderParserWitupTest
 */
import sun.net.www.HeaderParser;

public class HeaderParserWitupTest {

    public static void main(String[] args) {
        HeaderParser p = new HeaderParser("timeout=15, max=5");
        HeaderParser sub = p.subsequence(0, 1);
        if (sub == null) {
            throw new RuntimeException("subsequence returned null");
        }
        expectIAE(p, -1, 1);
        expectIAE(p, 1, 1);
        expectIAE(p, 0, 3);
    }

    static void expectIAE(HeaderParser p, int start, int end) {
        try {
            p.subsequence(start, end);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for " + start + "," + end);
    }
}
