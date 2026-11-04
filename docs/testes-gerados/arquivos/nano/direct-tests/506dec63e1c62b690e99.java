/* @test    @summary Test for Pair.qname() method to ensure it returns the correct string.    @run main PairWitupTest */

import jdk.internal.util.xml.impl.Pair;

public class PairWitupTest {

    public static void main(String[] args) {
        testQnameReturnsCorrectString();
    }

    public static void testQnameReturnsCorrectString() {
        // Arrange
        Pair pair = new Pair();
        pair.chars = new char[] {'\0', 'a', 'b', 'c'}; // length 4, with first char 0
        // Act
        String result = pair.qname();
        // Assert
        if (!"abc".equals(result)) {
            throw new AssertionError("Expected 'abc', but got: " + result);
        }
    }
}
