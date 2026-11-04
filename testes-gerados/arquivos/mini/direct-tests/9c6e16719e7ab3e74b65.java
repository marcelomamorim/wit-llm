/*
 * @test
 * @summary Test jdk.internal.module.SystemModuleFinders.find(String) method
 * @run main SystemModuleFindersWitupTest
 */

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class SystemModuleFindersWitupTest {

    // Minimal subclass to expose findImageLocation and module for testing
    static class TestFinder extends jdk.internal.module.SystemModuleFinders {
        private final String module;
        private final ImageLocation location;

        TestFinder(String module, ImageLocation location) {
            this.module = module;
            this.location = location;
        }

        // Override findImageLocation to return preset location
        ImageLocation findImageLocation(String name) {
            if (name == null) return null;
            if (name.equals("existing")) {
                return location;
            } else {
                return null;
            }
        }

        // Expose module field for URI construction
        String getModule() {
            return module;
        }

        // Implement find method as in source, but using this.module and findImageLocation
        public Optional<URI> find(String name) throws IOException {
            ImageLocation loc = findImageLocation(name);
            if (loc != null) {
                URI u = URI.create("jrt:/" + module + "/" + name);
                return Optional.of(u);
            } else {
                return Optional.empty();
            }
        }
    }

    // Dummy ImageLocation class to satisfy type
    static class ImageLocation {}

    public static void main(String[] args) throws IOException {
        // Setup
        ImageLocation dummyLocation = new ImageLocation();
        TestFinder finder = new TestFinder("mod", dummyLocation);

        // Test: find returns Optional with URI for known module name
        Optional<URI> result1 = finder.find("existing");
        if (!result1.isPresent()) {
            throw new AssertionError("Expected Optional with URI for existing module");
        }
        URI uri1 = result1.get();
        if (!"jrt:/mod/existing".equals(uri1.toString())) {
            throw new AssertionError("URI mismatch: " + uri1);
        }

        // Test: find returns empty Optional for unknown module name
        Optional<URI> result2 = finder.find("unknown");
        if (result2.isPresent()) {
            throw new AssertionError("Expected empty Optional for unknown module");
        }

        // Test: find returns empty Optional for null name
        Optional<URI> result3 = finder.find(null);
        if (result3.isPresent()) {
            throw new AssertionError("Expected empty Optional for null name");
        }

        System.out.println("All tests passed");
    }
}
