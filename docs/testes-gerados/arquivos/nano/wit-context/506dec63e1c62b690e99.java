/*
 * @test
 * @summary Test for jdk.internal.util.xml.impl.Pair.qname()
 * @run main PairWitupTest
 */

import jdk.internal.util.xml.impl.Pair;

public class PairWitupTest {

    public static void main(String[] args) {
        testQnameWithEmptyChars();
        testQnameWithValidChars();
    }

    private static void testQnameWithEmptyChars() {
        Pair pair = new Pair();
        pair.chars = new char[0];
        try {
            String result = pair.qname();
            System.out.println("testQnameWithEmptyChars: FAILED - Expected exception, got: " + result);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("testQnameWithEmptyChars: PASSED");
        } catch (Exception e) {
            System.out.println("testQnameWithEmptyChars: FAILED - Unexpected exception: " + e);
        }
    }

    private static void testQnameWithValidChars() {
        Pair pair = new Pair();
        pair.chars = new char[] {'a', 'b', 'c', 'd'};
        try {
            String result = pair.qname();
            if ("bcd".equals(result)) {
                System.out.println("testQnameWithValidChars: PASSED");
            } else {
                System.out.println("testQnameWithValidChars: FAILED - Unexpected result: " + result);
            }
        } catch (Exception e) {
            System.out.println("testQnameWithValidChars: FAILED - Exception: " + e);
        }
    }
}
