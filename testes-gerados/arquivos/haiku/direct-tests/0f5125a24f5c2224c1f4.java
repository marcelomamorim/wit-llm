/*
 * @test
 * @summary Checks HeaderParser.subsequence identity and argument validation on empty parsers
 * @modules java.base/sun.net.www
 * @run main HeaderParserWitupTest
 */

import sun.net.www.HeaderParser;

public class HeaderParserWitupTest {

    public static void main(String[] args) {
        HeaderParser nullParser = new HeaderParser(null);
        if (nullParser.subsequence(0, 0) != nullParser) {
            throw new RuntimeException("subsequence(0, nkeys) must return this for null raw");
        }

        HeaderParser emptyParser = new HeaderParser("");
        if (emptyParser.subsequence(0, 0) != emptyParser) {
            throw new RuntimeException("subsequence(0, nkeys) must return this for empty raw");
        }

        expectIllegalArgument(nullParser, -1, 0);
        expectIllegalArgument(nullParser, 0, 1);
        expectIllegalArgument(nullParser, 1, 1);
        expectIllegalArgument(nullParser, 2, 1);
        expectIllegalArgument(emptyParser, 0, 1);
        expectIllegalArgument(emptyParser, -2, -1);
    }

    private static void expectIllegalArgument(HeaderParser p, int start, int end) {
        try {
            p.subsequence(start, end);
        } catch (IllegalArgumentException e) {
            if (!"invalid start or end".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException for start=" + start + " end=" + end);
    }
}
