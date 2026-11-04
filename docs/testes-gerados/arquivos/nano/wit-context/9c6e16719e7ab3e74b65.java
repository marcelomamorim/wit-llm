/*
 * @test
 * @summary Test for SystemModuleFinders.find(String)
 * @run main SystemModuleFindersWitupTest
 */

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws IOException {
        testFindWithExistingModuleName();
        testFindWithNonExistingModuleName();
    }

    private static void testFindWithExistingModuleName() throws IOException {
        String existingModuleName = "java.base"; // Assumed to exist in the runtime
        Optional<URI> result = new SystemModuleFinders().find(existingModuleName);
        if (result.isPresent()) {
            URI uri = result.get();
            if (!uri.toString().startsWith("jrt:/")) {
                throw new AssertionError("Expected URI to start with 'jrt:/', but was: " + uri);
            }
        } else {
            throw new AssertionError("Expected non-empty result for existing module name") ;
        }
    }

    private static void testFindWithNonExistingModuleName() throws IOException {
        String nonExistingModuleName = "non.existent.module";
        Optional<URI> result = new SystemModuleFinders().find(nonExistingModuleName);
        if (result.isPresent()) {
            throw new AssertionError("Expected empty result for non-existing module name, but got: " + result.get());
        }
    }
}
