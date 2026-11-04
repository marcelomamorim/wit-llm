/* @test    @summary Test for Formatter.out() method to verify behavior when Formatter is closed.    @run main FormatterWitupTest */

import java.util.Formatter;
import java.io.IOException;

public class FormatterWitupTest {

    public static void main(String[] args) {
        testOutWhenOpen();
        testOutWhenClosed();
    }

    static void testOutWhenOpen() {
        Formatter formatter = new Formatter();
        try {
            Appendable out = formatter.out();
            if (out != null) {
                System.out.println("testOutWhenOpen: PASSED");
            } else {
                System.out.println("testOutWhenOpen: FAILED - out() returned null");
            }
        } catch (Exception e) {
            System.out.println("testOutWhenOpen: FAILED with exception: " + e);
        } finally {
            formatter.close();
        }
    }

    static void testOutWhenClosed() {
        Formatter formatter = new Formatter();
        formatter.close();
        try {
            formatter.out();
            System.out.println("testOutWhenClosed: FAILED - expected exception not thrown");
        } catch (IllegalStateException e) {
            System.out.println("testOutWhenClosed: PASSED");
        } catch (Exception e) {
            System.out.println("testOutWhenClosed: FAILED with unexpected exception: " + e);
        }
    }
}
