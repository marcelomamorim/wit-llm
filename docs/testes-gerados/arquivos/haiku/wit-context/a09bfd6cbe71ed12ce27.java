/*
 * @test
 * @summary Checks that HeaderParser.subsequence rejects invalid ranges with IllegalArgumentException
 * @modules java.base/sun.net.www
 * @run main HeaderParserWitupTest
 */

import sun.net.www.HeaderParser;

public class HeaderParserWitupTest {

    public static void main(String[] args) {
        HeaderParser hp = new HeaderParser("a=b, c=d");
        expectIAE(hp, -1, 1);
        expectIAE(hp, 1, 1);
        expectIAE(hp, 2, 1);
        expectIAE(hp, 0, 99);
    }

    private static void expectIAE(HeaderParser hp, int start, int end) {
        try {
            hp.subsequence(start, end);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for start=" + start + " end=" + end);
    }
}
