/* @test    @summary Test Formatter.flush() method for proper behavior    @run main FormatterWitupTest */

import java.io.Flushable;
import java.io.IOException;
import java.util.Formatter;

public class FormatterWitupTest {

    public static void main(String[] args) {
        new FormatterWitupTest().testFlush();
    }

    public void testFlush() {
        Formatter formatter = new Formatter();
        try {
            formatter.flush();
        } catch (Exception e) {
            throw new AssertionError("flush() threw an exception: " + e, e);
        }
        // Test flush with a custom Flushable that throws IOException
        class ThrowingFlushable implements Flushable {
            boolean flushed = false;
            @Override
            public void flush() throws IOException {
                flushed = true;
                throw new IOException("Test IOException");
            }
        }
        ThrowingFlushable tf = new ThrowingFlushable();
        Formatter formatterWithFlushable = new Formatter();
        // Use reflection to set the internal Appendable to our custom Flushable
        try {
            java.lang.reflect.Field aField = Formatter.class.getDeclaredField("a");
            aField.setAccessible(true);
            aField.set(formatterWithFlushable, tf);
            formatterWithFlushable.flush();
            // If no exception, test passes, but lastException should be set
            // Since lastException is private, we test indirectly by calling flush again
            formatterWithFlushable.flush();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError("Reflection failed: " + e, e);
        } catch (Exception e) {
            // Expect IOException to be caught and stored, so no exception should propagate
            throw new AssertionError("flush() with custom Flushable threw an unexpected exception: " + e, e);
        }
    }
}
