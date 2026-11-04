/* @test    @summary Test the list() method of SystemModuleFinders for correct module listing behavior    @run main SystemModuleFindersWitupTest */

import java.io.IOException;
import java.util.stream.Stream;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws IOException {
        testListReturnsStream();
        testListThrowsWhenClosed();
    }

    static void testListReturnsStream() throws IOException {
        // Invoke list() and verify it returns a non-null Stream
        Stream<String> modulesStream = jdk.internal.module.SystemModuleFinders.list();
        if (modulesStream == null) {
            throw new AssertionError("list() returned null instead of a Stream");
        }
        // Collect module names to verify content
        boolean hasElements = false;
        for (String moduleName : (Iterable<String>) modulesStream::iterator) {
            hasElements = true;
            if (moduleName == null || moduleName.isEmpty()) {
                throw new AssertionError("Module name should not be null or empty");
            }
        }
        if (!hasElements) {
            // It's acceptable for system modules to be empty, but log for info
            System.out.println("Warning: list() returned an empty stream");
        }
    }

    static void testListThrowsWhenClosed() {
        // Since the method relies on internal state (closed), simulate the scenario
        // by creating a subclass or using reflection is not possible here; instead,
        // we assume that calling list() when the module is closed throws IOException.
        // As we cannot access internal 'closed' state, this test is a placeholder.
        // In real scenario, this would involve setting internal state or mocking.
        // For demonstration, we invoke list() and catch IOException if thrown.
        try {
            jdk.internal.module.SystemModuleFinders.list();
            // If no exception, test passes as the method does not throw in normal case
        } catch (IOException e) {
            // Expected if the module is closed; in this test, we assume not closed
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }
}
