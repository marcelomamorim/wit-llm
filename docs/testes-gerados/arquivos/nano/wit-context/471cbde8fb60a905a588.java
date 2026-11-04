/* @test    @summary Test for StringBuffer.codePointBefore(int) method to verify exception handling and behavior.    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testCodePointBeforeWithInvalidIndex();
        testCodePointBeforeWithValidIndex();
    }

    private static void testCodePointBeforeWithInvalidIndex() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.codePointBefore(-1);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for index -1");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for index -1");
        }
        try {
            sb.codePointBefore(4);
            System.out.println("FAIL: Expected StringIndexOutOfBoundsException for index 4");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("PASS: Caught expected exception for index 4");
        }
    }

    private static void testCodePointBeforeWithValidIndex() {
        StringBuffer sb = new StringBuffer("abc");
        int cp = sb.codePointBefore(1); // should return code point of 'a'
        if (cp == 'a') {
            System.out.println("PASS: codePointBefore(1) returned 'a'");
        } else {
            System.out.println("FAIL: codePointBefore(1) returned " + cp);
        }
        cp = sb.codePointBefore(3); // should return code point of 'c'
        if (cp == 'c') {
            System.out.println("PASS: codePointBefore(3) returned 'c'");
        } else {
            System.out.println("FAIL: codePointBefore(3) returned " + cp);
        }
    }
}
