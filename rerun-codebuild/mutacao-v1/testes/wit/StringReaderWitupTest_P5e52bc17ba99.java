/* @test    @summary Test for StringReader.mark(int) method handling of invalid readAheadLimit values    @run main StringReaderWitupTest_P5e52bc17ba99 */

import java.io.StringReader;
import java.io.IOException;

public class StringReaderWitupTest_P5e52bc17ba99 {

    public static void main(String[] args) {
        testMarkWithNegativeReadAheadLimit();
    }

    private static void testMarkWithNegativeReadAheadLimit() {
        String sample = "Sample text";
        StringReader reader = new StringReader(sample);
        try {
            reader.mark(-1);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().contains("Read-ahead limit < 0")) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e);
        }
    }
}
