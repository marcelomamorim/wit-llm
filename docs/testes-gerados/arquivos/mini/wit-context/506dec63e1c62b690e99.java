/*
 * @test
 * @summary Test jdk.internal.util.xml.impl.Pair.qname() method for normal and boundary cases
 * @run main PairWitupTest
 */

public class PairWitupTest {
    public static void main(String[] args) {
        testQnameNormal();
        testQnameEmptyChars();
        testQnameSingleChar();
    }

    private static void testQnameNormal() {
        // chars length > 1, normal case
        jdk.internal.util.xml.impl.Pair p = new jdk.internal.util.xml.impl.Pair();
        p.chars = new char[] {3, 'a', 'b', 'c'}; // length 4
        String qname = p.qname();
        // Expected substring from index 1 to end: "abc"
        if (!"abc".equals(qname)) {
            throw new AssertionError("Expected 'abc' but got '" + qname + "'");
        }
    }

    private static void testQnameEmptyChars() {
        // chars length == 0, expect StringIndexOutOfBoundsException
        jdk.internal.util.xml.impl.Pair p = new jdk.internal.util.xml.impl.Pair();
        p.chars = new char[0];
        try {
            p.qname();
            throw new AssertionError("Expected StringIndexOutOfBoundsException for empty chars array");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }

    private static void testQnameSingleChar() {
        // chars length == 1, expect StringIndexOutOfBoundsException because length-1 == 0, substring(1,0) invalid
        jdk.internal.util.xml.impl.Pair p = new jdk.internal.util.xml.impl.Pair();
        p.chars = new char[] {'x'}; // length 1
        try {
            p.qname();
            throw new AssertionError("Expected StringIndexOutOfBoundsException for single char array");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
