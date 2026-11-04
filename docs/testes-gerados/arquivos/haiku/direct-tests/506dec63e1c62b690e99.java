/*
 * @test
 * @summary Verifies Pair.qname() returns the characters after the leading length/prefix slot
 * @modules java.base/jdk.internal.util.xml.impl
 * @run main PairWitupTest
 */

public class PairWitupTest {

    public static void main(String[] args) {
        Pair p = new Pair();

        p.chars = new char[] {2, 'p', ':', 'x'};
        check("p:x".equals(p.qname()), "prefixed qname should include prefix and local part");

        p.chars = new char[] {0, 'a', 'b', 'c'};
        check("abc".equals(p.qname()), "unprefixed qname should return all characters after index 0");

        p.chars = new char[] {1};
        check("".equals(p.qname()), "single-slot chars should yield empty qname");

        p.chars = new char[0];
        try {
            p.qname();
            throw new RuntimeException("expected IndexOutOfBoundsException for empty chars");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
