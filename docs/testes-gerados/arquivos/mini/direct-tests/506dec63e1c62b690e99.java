/*
 * @test
 * @summary Test jdk.internal.util.xml.impl.Pair.qname() method
 * @run main PairWitupTest
 */

public class PairWitupTest {
    public static void main(String[] args) {
        testQnameNormal();
        testQnameSingleCharArray();
        testQnameEmptyChars();
    }

    private static void testQnameNormal() {
        jdk.internal.util.xml.impl.Pair p = new jdk.internal.util.xml.impl.Pair();
        p.chars = new char[] {'a', 'b', 'c', 'd'}; // length 4
        String qname = p.qname();
        // Expected substring from index 1 length 3: "bcd"
        if (!"bcd".equals(qname)) {
            throw new AssertionError("Expected 'bcd' but got '" + qname + "'");
        }
    }

    private static void testQnameSingleCharArray() {
        jdk.internal.util.xml.impl.Pair p = new jdk.internal.util.xml.impl.Pair();
        p.chars = new char[] {'x'}; // length 1
        String qname = p.qname();
        // Expected substring from index 1 length 0: empty string
        if (!"".equals(qname)) {
            throw new AssertionError("Expected empty string but got '" + qname + "'");
        }
    }

    private static void testQnameEmptyChars() {
        jdk.internal.util.xml.impl.Pair p = new jdk.internal.util.xml.impl.Pair();
        p.chars = new char[0]; // empty array
        try {
            p.qname();
            throw new AssertionError("Expected StringIndexOutOfBoundsException for empty chars array");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
