/* @test    @summary Test for String.format with null arguments to observe FormatterClosedException behavior    @run main StringWitupTest */

import java.lang.String;
import java.util.Formatter;

public class StringWitupTest {

    public static void main(String[] args) {
        testFormatWithNullArgs();
    }

    public static void testFormatWithNullArgs() {
        String formatString = "%s";
        Object[] args = null;
        try {
            String result = String.format(formatString, args);
            // If no exception, print result for observation
            System.out.println("Result: " + result);
        } catch (FormatterClosedException e) {
            System.out.println("Caught FormatterClosedException as expected when args is null.");
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException as expected when args is null.");
        } catch (Exception e) {
            System.out.println("Caught unexpected exception: " + e);
        }
    }
}
