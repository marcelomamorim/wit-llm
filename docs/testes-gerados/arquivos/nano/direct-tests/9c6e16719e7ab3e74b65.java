/*
 * @test
 * @summary Test for SystemModuleFinders.find method
 * @run main SystemModuleFindersWitupTest
 */

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    public static void main(String[] args) throws IOException {
        testFindExistingModule();
        testFindNonExistingModule();
    }

    static void testFindExistingModule() throws IOException {
        String moduleName = "java.base";
        Optional<URI> result = jdk.internal.module.SystemModuleFinders.find(moduleName);
        if (result.isPresent()) {
            URI uri = result.get();
            assert uri.toString().startsWith("jrt:/") : "Expected URI to start with 'jrt:/', but was: " + uri;
        } else {
            throw new AssertionError("Expected to find module: " + moduleName);
        }
    }

    static void testFindNonExistingModule() throws IOException {
        String moduleName = "non.existent.module";
        Optional<URI> result = jdk.internal.module.SystemModuleFinders.find(moduleName);
        assert !result.isPresent() : "Expected no result for non-existent module";
    }
}
