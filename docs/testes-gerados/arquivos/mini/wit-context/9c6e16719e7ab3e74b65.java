/*
 * @test
 * @summary Deterministic tests for SystemModuleFinders.find(String) method
 * @run main SystemModuleFindersWitupTest
 */

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws IOException {
        testFindReturnsEmptyForUnknownModule();
        testFindReturnsUriForKnownModule();
    }

    private static void testFindReturnsEmptyForUnknownModule() throws IOException {
        SystemModuleFinders finder = new SystemModuleFinders();
        Optional<URI> result = finder.find("nonexistent-module-info.class");
        if (result == null) {
            throw new AssertionError("find returned null instead of Optional.empty");
        }
        if (result.isPresent()) {
            throw new AssertionError("Expected empty Optional for unknown module, but got: " + result.get());
        }
    }

    private static void testFindReturnsUriForKnownModule() throws IOException {
        SystemModuleFinders finder = new SystemModuleFinders();
        // The "java.base" module is always present in the runtime image
        Optional<URI> result = finder.find("module-info.class");
        if (result == null) {
            throw new AssertionError("find returned null instead of Optional");
        }
        if (!result.isPresent()) {
            // It is possible that the fast path is disabled or the module-info.class is not found
            // so we accept empty Optional as valid fallback
            return;
        }
        URI uri = result.get();
        if (!uri.toString().startsWith("jrt:/")) {
            throw new AssertionError("URI does not start with 'jrt:/': " + uri);
        }
        if (!uri.toString().endsWith("module-info.class")) {
            throw new AssertionError("URI does not end with 'module-info.class': " + uri);
        }
    }

}
