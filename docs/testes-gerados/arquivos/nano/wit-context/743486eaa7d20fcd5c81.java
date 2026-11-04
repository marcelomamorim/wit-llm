/*
 * @test
 * @summary Test for StringLatin1.forEachRemaining with null Consumer
 * @run main StringLatin1WitupTest
 */

import java.lang.StringLatin1;
import java.util.function.Consumer;

public class StringLatin1WitupTest {

    public static void main(String[] args) {
        testForEachRemainingNullAction();
    }

    public static void testForEachRemainingNullAction() {
        byte[] data = {0x01, 0x02, 0x03};
        StringLatin1.LinesSpliterator spliterator = new StringLatin1.LinesSpliterator(data, 0, data.length);
        try {
            spliterator.forEachRemaining(null);
            System.out.println("FAIL: Expected NullPointerException was not thrown.");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected.");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
