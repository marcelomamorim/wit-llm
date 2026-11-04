/*
 * @test
 * @summary Test StringUTF16.putCharsSB method for correctness and bounds checking
 * @run main StringUTF16WitupTest
 */

public class StringUTF16WitupTest {

    public static void main(String[] args) {
        testPutCharsSBBasic();
        testPutCharsSBEmptySequence();
        testPutCharsSBFullRange();
        testPutCharsSBBoundsCheck();
    }

    private static void testPutCharsSBBasic() {
        byte[] val = new byte[10];
        String s = "AB";
        // putCharsSB should write chars 'A' and 'B' starting at index 0
        java.lang.StringUTF16.putCharsSB(val, 0, s, 0, s.length());
        // read back chars
        char c0 = java.lang.StringUTF16.getChar(val, 0);
        char c1 = java.lang.StringUTF16.getChar(val, 1);
        if (c0 != 'A' || c1 != 'B') {
            throw new AssertionError("Basic putCharsSB failed: expected 'A','B' but got '" + c0 + "','" + c1 + "'");
        }
    }

    private static void testPutCharsSBEmptySequence() {
        byte[] val = new byte[4];
        String s = "";
        // Should not throw and not modify val
        byte[] valCopy = val.clone();
        java.lang.StringUTF16.putCharsSB(val, 0, s, 0, 0);
        for (int i = 0; i < val.length; i++) {
            if (val[i] != valCopy[i]) {
                throw new AssertionError("putCharsSB modified array for empty CharSequence");
            }
        }
    }

    private static void testPutCharsSBFullRange() {
        byte[] val = new byte[20];
        String s = "HelloWorld";
        // Write full string into val starting at index 0
        java.lang.StringUTF16.putCharsSB(val, 0, s, 0, s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = java.lang.StringUTF16.getChar(val, i);
            if (c != s.charAt(i)) {
                throw new AssertionError("putCharsSB full range failed at index " + i + ": expected '" + s.charAt(i) + "' but got '" + c + "'");
            }
        }
    }

    private static void testPutCharsSBBoundsCheck() {
        byte[] val = new byte[4];
        String s = "AB";
        boolean caught = false;
        try {
            // index + (end - off) = 3 + (2 - 0) = 5 > val.length/2 = 2, should throw
            java.lang.StringUTF16.putCharsSB(val, 3, s, 0, s.length());
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        if (!caught) {
            throw new AssertionError("putCharsSB did not throw IndexOutOfBoundsException on invalid bounds");
        }
    }

}
