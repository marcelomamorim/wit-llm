/*
 * @test
 * @summary Verifies bounds checking of AttributedCharacterIterator.setIndex on an AttributedString iterator
 * @run main AttributedStringWitupTest
 */

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;

public class AttributedStringWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    private static void expectIllegalArgument(AttributedCharacterIterator it, int position) {
        int before = it.getIndex();
        try {
            it.setIndex(position);
            check(false, "setIndex(" + position + ") should throw IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            check(it.getIndex() == before,
                  "index must be unchanged after rejected setIndex(" + position + ")");
        }
    }

    public static void main(String[] args) {
        AttributedString as = new AttributedString("abc");
        AttributedCharacterIterator it = as.getIterator();

        check(it.setIndex(0) == 'a', "setIndex(0) should return 'a'");
        check(it.setIndex(2) == 'c', "setIndex(2) should return 'c'");
        check(it.setIndex(3) == CharacterIterator.DONE, "setIndex(endIndex) should return DONE");
        check(it.getIndex() == 3, "getIndex() should be 3 after setIndex(3)");

        expectIllegalArgument(it, -1);
        expectIllegalArgument(it, 4);

        AttributedString empty = new AttributedString("");
        AttributedCharacterIterator emptyIt = empty.getIterator();
        check(emptyIt.setIndex(0) == CharacterIterator.DONE, "empty setIndex(0) should return DONE");
        expectIllegalArgument(emptyIt, 1);

        AttributedString sub = new AttributedString(as.getIterator(), 1, 3);
        AttributedCharacterIterator subIt = sub.getIterator();
        check(subIt.setIndex(0) == 'b', "substring setIndex(0) should return 'b'");
        check(subIt.setIndex(2) == CharacterIterator.DONE, "substring setIndex(2) should return DONE");
        expectIllegalArgument(subIt, 3);

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
