/* @test    @summary Test for UCharacterIterator.getInstance(StringBuffer) with null input    @run main UCharacterIteratorWitupTest */

import sun.text.normalizer.UCharacterIterator;
import java.text.CharacterIterator;

public class UCharacterIteratorWitupTest {

    public static void main(String[] args) {
        testGetInstanceWithNull();
        testGetInstanceWithValid();
    }

    private static void testGetInstanceWithNull() {
        try {
            UCharacterIterator.getInstance((StringBuffer) null);
            System.err.println("Failed: Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            System.out.println("Passed: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.err.println("Failed: Unexpected exception: " + e);
        }
    }

    private static void testGetInstanceWithValid() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            UCharacterIterator it = UCharacterIterator.getInstance(sb);
            if (it != null) {
                System.out.println("Passed: Valid StringBuffer input");
            } else {
                System.err.println("Failed: Null returned for valid input");
            }
        } catch (Exception e) {
            System.err.println("Failed: Exception for valid input: " + e);
        }
    }
}
