/*
 * @test
 * @summary Test for java.util.Formatter.flush() method.
 * @run main FormatterWitupTest
 */

import java.util.Formatter;
import java.io.IOException;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testFlushWithNull();
        testFlushWithOpenFormatter();
    }

    public static void testFlushWithNull() {
        Formatter formatter = null;
        try {
            formatter.flush();
        } catch (NullPointerException e) {
            System.out.println("Caught expected NullPointerException");
            return;
        }
        throw new AssertionError("Expected NullPointerException not thrown");
    }

    public static void testFlushWithOpenFormatter() {
        Formatter formatter = new Formatter();
        try {
            formatter.flush();
            System.out.println("flush() executed successfully on open Formatter");
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception during flush(): " + e);
        } finally {
            formatter.close();
        }
    }
}
