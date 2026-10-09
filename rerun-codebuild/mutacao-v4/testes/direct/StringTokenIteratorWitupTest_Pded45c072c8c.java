/* @test
   @summary Tests the setStart method of StringTokenIterator for valid and invalid inputs.
   @run main StringTokenIteratorWitupTest_Pded45c072c8c
 */
import java.util.Objects;
import sun.util.locale.StringTokenIterator;

public class StringTokenIteratorWitupTest_Pded45c072c8c {
    public static void main(String[] args) {
        testSetStartValid();
        testSetStartInvalid();
    }

    private static void testSetStartValid() {
        String text = "a,b,c";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        // Move to second token
        iterator.next();
        // Set start to beginning
        iterator.setStart(0);
        assert "a".equals(iterator.current()) : "Expected current token to be 'a'";
        // Set start to middle of string
        iterator.setStart(2);
        assert "b".equals(iterator.current()) : "Expected current token to be 'b'";
    }

    private static void testSetStartInvalid() {
        String text = "abc";
        String dlms = ",";
        StringTokenIterator iterator = new StringTokenIterator(text, dlms);
        boolean exceptionThrown = false;
        try {
            iterator.setStart(10); // invalid offset
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        assert exceptionThrown : "Expected IndexOutOfBoundsException for offset beyond text length";
    }
}
