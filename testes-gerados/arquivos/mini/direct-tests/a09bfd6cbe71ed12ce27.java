/*
 * @test
 * @summary Test HeaderParser.remove() throws UnsupportedOperationException
 * @run main HeaderParserWitupTest
 */

public class HeaderParserWitupTest {
    public static void main(String[] args) {
        HeaderParser hp = new HeaderParser("key=value");
        try {
            hp.remove();
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException e) {
            // expected
        }
    }
}

class HeaderParser {
    String raw;
    String[][] tab;
    int nkeys;
    int asize = 10;

    public HeaderParser(String raw) {
        this.raw = raw;
        tab = new String[asize][2];
        parse();
    }

    private HeaderParser() {
    }

    private void parse() {
        // minimal parse to allow construction
        if (raw != null) {
            raw = raw.trim();
            if (!raw.isEmpty()) {
                tab[0][0] = raw.toLowerCase();
                nkeys = 1;
            }
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
}
