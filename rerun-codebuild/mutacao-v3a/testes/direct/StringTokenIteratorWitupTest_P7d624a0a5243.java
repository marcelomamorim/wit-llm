/* @test    @summary Test StringTokenIterator.setStart with valid and invalid inputs    @run main StringTokenIteratorWitupTest_P7d624a0a5243 */

import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest_P7d624a0a5243 {

    public static void main(String[] args) {
        testSetStartValid();
        testSetStartInvalid();
    }

    static void testSetStartValid() {
        String text = "a,b,c,d";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        // Set start to 2 which is in the middle of the string
        iterator.setStart(2);
        assert "b".equals(iterator.current()) : "Expected current token to be 'b'";
        assert iterator.currentStart() == 2 : "Expected start index 2";
        assert iterator.currentEnd() == 3 : "Expected end index 3";
        assert !iterator.isDone() : "Iterator should not be done";
    }

    static void testSetStartInvalid() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        boolean thrown = false;
        try {
            iterator.setStart(10); // Out of bounds
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        assert thrown : "Expected IndexOutOfBoundsException for offset > text length";
    }
}
