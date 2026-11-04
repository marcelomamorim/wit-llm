/*
 * @test
 * @summary Witup-generated checks for Pair.qname() on normal and empty char arrays
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main PairWitupTest
 */

public class PairWitupTest {

    public static void main(String[] args) {
        Pair p = new Pair();

        p.chars = "xyz".toCharArray();
        String q = p.qname();
        if (!"yz".equals(q)) {
            throw new RuntimeException("Expected qname 'yz' but got '" + q + "'");
        }

        p.chars = "a".toCharArray();
        q = p.qname();
        if (!"".equals(q)) {
            throw new RuntimeException("Expected empty qname but got '" + q + "'");
        }

        p.chars = new char[0];
        boolean thrown = false;
        try {
            p.qname();
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for empty chars");
        }
    }
}
