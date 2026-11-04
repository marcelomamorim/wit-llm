/*
 * @test
 * @summary Test StringBuffer.substring(int start) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testSubstringValidStart();
        testSubstringStartAtZero();
        testSubstringStartAtLength();
    }

    private static void testSubstringValidStart() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("Hello, world!");
        String result = sb.substring(7);
        if (!"world!".equals(result)) {
            throw new Error("Expected 'world!' but got '" + result + "'");
        }
    }

    private static void testSubstringStartAtZero() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("Hello");
        String result = sb.substring(0);
        if (!"Hello".equals(result)) {
            throw new Error("Expected 'Hello' but got '" + result + "'");
        }
    }

    private static void testSubstringStartAtLength() {
        java.lang.StringBuffer sb = new java.lang.StringBuffer("Test");
        String result = sb.substring(sb.length());
        if (!"".equals(result)) {
            throw new Error("Expected empty string but got '" + result + "'");
        }
    }
}