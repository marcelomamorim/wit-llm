/*
 * @test
 * @summary Test for StringUTF16.putCharsSB method, focusing on boundary conditions.
 * @run main StringUTF16WitupTest
 */

import java.lang.StringUTF16;

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testPutCharsSB_NormalCase();
        testPutCharsSB_BoundsCheck();
        testPutCharsSB_InvalidBounds();
    }

    private static void testPutCharsSB_NormalCase() {
        byte[] buffer = new byte[20];
        String s = "abcde";
        try {
            StringUTF16.putCharsSB(buffer, 0, s, 0, s.length());
            System.out.println("testPutCharsSB_NormalCase passed");
        } catch (Exception e) {
            System.err.println("testPutCharsSB_NormalCase failed: " + e);
        }
    }

    private static void testPutCharsSB_BoundsCheck() {
        byte[] buffer = new byte[10];
        String s = "abcdef";
        // This should succeed
        try {
            StringUTF16.putCharsSB(buffer, 2, s, 0, 4);
            System.out.println("testPutCharsSB_BoundsCheck success");
        } catch (Exception e) {
            System.err.println("testPutCharsSB_BoundsCheck failed: " + e);
        }
        // This should throw StringIndexOutOfBoundsException
        try {
            StringUTF16.putCharsSB(buffer, 8, s, 0, 4);
            System.err.println("testPutCharsSB_BoundsCheck failed: no exception thrown");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("testPutCharsSB_BoundsCheck caught expected exception");
        } catch (Exception e) {
            System.err.println("testPutCharsSB_BoundsCheck failed: " + e);
        }
    }

    private static void testPutCharsSB_InvalidBounds() {
        byte[] buffer = new byte[5];
        String s = "hello";
        // off + end exceeds buffer length
        try {
            StringUTF16.putCharsSB(buffer, 0, s, 0, 10);
            System.err.println("testPutCharsSB_InvalidBounds failed: no exception");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("testPutCharsSB_InvalidBounds caught expected exception");
        } catch (Exception e) {
            System.err.println("testPutCharsSB_InvalidBounds failed: " + e);
        }
        // negative index
        try {
            StringUTF16.putCharsSB(buffer, -1, s, 0, 3);
            System.err.println("testPutCharsSB_InvalidBounds failed: no exception");
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("testPutCharsSB_InvalidBounds caught expected exception");
        } catch (Exception e) {
            System.err.println("testPutCharsSB_InvalidBounds failed: " + e);
        }
    }
}
