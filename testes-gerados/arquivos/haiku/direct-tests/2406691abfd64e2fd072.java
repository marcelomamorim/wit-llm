/*
 * @test
 * @summary ClassLoader.getResource null check, parent delegation and fallback to findResource
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;

public class ClassLoaderWitupTest {

    static final class Fixed extends ClassLoader {
        private final URL url;

        Fixed(ClassLoader parent, URL url) {
            super(parent);
            this.url = url;
        }

        @Override
        protected URL findResource(String name) {
            return url;
        }
    }

    public static void main(String[] args) throws Exception {
        // null name must be rejected by Objects.requireNonNull
        try {
            new Fixed(null, null).getResource(null);
            throw new RuntimeException("NullPointerException expected for null name");
        } catch (NullPointerException expected) {
            // expected
        }

        // no parent: falls back to findResource when bootstrap has no such resource
        URL own = new URL("file:/witup/own.txt");
        Fixed noParent = new Fixed(null, own);
        if (noParent.getResource("witup/nonexistent/ClassLoaderWitupResource.txt") != own) {
            throw new RuntimeException("findResource result not returned when no parent");
        }

        // parent result takes precedence; child's findResource is not used
        URL parentUrl = new URL("file:/parent.txt");
        URL childUrl = new URL("file:/child.txt");
        Fixed parent = new Fixed(null, parentUrl);
        Fixed child = new Fixed(parent, childUrl);
        if (child.getResource("witup/nonexistent/ClassLoaderWitupResource.txt") != parentUrl) {
            throw new RuntimeException("parent resource should take precedence");
        }

        // parent returns null: child's findResource is used
        Fixed silentParent = new Fixed(null, null);
        Fixed child2 = new Fixed(silentParent, childUrl);
        if (child2.getResource("witup/nonexistent/ClassLoaderWitupResource.txt") != childUrl) {
            throw new RuntimeException("child findResource should be used when parent returns null");
        }
    }
}
