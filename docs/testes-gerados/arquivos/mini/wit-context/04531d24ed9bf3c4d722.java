/*
 * @test
 * @summary Test java.lang.String.format(String, Object...) for expected behavior and exception
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testFormatValid();
        testFormatNullArgs();
    }

    private static void testFormatValid() {
        String result = String.format("Hello %s!", "World");
        if (!"Hello World!".equals(result)) {
            throw new AssertionError("Expected 'Hello World!' but got: " + result);
        }
    }

    private static void testFormatNullArgs() {
        // According to current source, passing null as args throws FormatterClosedException
        // because new Formatter().format(format, args) calls Formatter.ensureOpen() which throws if closed
        // The expath suggests FormatterClosedException when args == null
        // We test that passing null as args triggers FormatterClosedException
        try {
            String.format("test", (Object[]) null);
            throw new AssertionError("Expected java.util.FormatterClosedException when args is null");
        } catch (java.util.FormatterClosedException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Expected FormatterClosedException but got " + e);
        }
    }
}
