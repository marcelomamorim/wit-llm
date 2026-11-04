/*
 * @test
 * @summary Verifies CompoundEnumeration.nextElement via ClassLoader.getResources ordering and exhaustion behavior
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

public class ClassLoaderWitupTest {

    static final String RESOURCE = "witup/nonexistent/Resource.txt";

    static class StubLoader extends ClassLoader {
        private final List<URL> urls;

        StubLoader(ClassLoader parent, List<URL> urls) {
            super(parent);
            this.urls = urls;
        }

        @Override
        protected Enumeration<URL> findResources(String name) {
            final List<URL> copy = new ArrayList<>(urls);
            return new Enumeration<URL>() {
                int i = 0;
                public boolean hasMoreElements() { return i < copy.size(); }
                public URL nextElement() {
                    if (i >= copy.size()) throw new NoSuchElementException();
                    return copy.get(i++);
                }
            };
        }
    }

    public static void main(String[] args) throws Exception {
        URL parentUrl = new URL("file:/witup/parent");
        URL childUrl = new URL("file:/witup/child");

        // Parent with null grandparent: bootstrap lookup yields nothing for RESOURCE.
        StubLoader parent = new StubLoader(null, List.of(parentUrl));
        StubLoader child = new StubLoader(parent, List.of(childUrl));

        Enumeration<URL> e = child.getResources(RESOURCE);

        if (!e.hasMoreElements()) {
            throw new RuntimeException("expected at least one element");
        }
        URL first = e.nextElement();
        if (!first.toExternalForm().equals(parentUrl.toExternalForm())) {
            throw new RuntimeException("first element should come from parent: " + first);
        }

        if (!e.hasMoreElements()) {
            throw new RuntimeException("expected second element");
        }
        URL second = e.nextElement();
        if (!second.toExternalForm().equals(childUrl.toExternalForm())) {
            throw new RuntimeException("second element should come from findResources: " + second);
        }

        if (e.hasMoreElements()) {
            throw new RuntimeException("enumeration should be exhausted");
        }

        boolean thrown = false;
        try {
            e.nextElement();
        } catch (NoSuchElementException ex) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("nextElement on exhausted enumeration must throw NoSuchElementException");
        }

        // Child without any resources from findResources still yields parent's elements.
        StubLoader emptyChild = new StubLoader(parent, List.of());
        Enumeration<URL> e2 = emptyChild.getResources(RESOURCE);
        if (!e2.hasMoreElements()) {
            throw new RuntimeException("parent element missing");
        }
        if (!e2.nextElement().toExternalForm().equals(parentUrl.toExternalForm())) {
            throw new RuntimeException("unexpected parent element");
        }
        if (e2.hasMoreElements()) {
            throw new RuntimeException("unexpected extra element");
        }
    }
}
