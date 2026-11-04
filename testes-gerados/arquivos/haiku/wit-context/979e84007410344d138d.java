/*
 * @test
 * @summary Verifies that the enumeration returned by ClassLoader.getResources (a CompoundEnumeration) throws NoSuchElementException when exhausted
 * @run main ClassLoaderWitupTest
 */

import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class ClassLoaderWitupTest {

    static final String RESOURCE = "witup/generated/nonexistent/Resource.txt";

    static class EmptyLoader extends ClassLoader {
        EmptyLoader() {
            super(null);
        }
    }

    static class SingleUrlLoader extends ClassLoader {
        final URL url;

        SingleUrlLoader(URL url) {
            super(null);
            this.url = url;
        }

        @Override
        protected Enumeration<URL> findResources(String name) {
            return Collections.enumeration(Collections.singletonList(url));
        }
    }

    static void expectNoSuchElement(Enumeration<URL> e) {
        try {
            e.nextElement();
        } catch (NoSuchElementException expected) {
            return;
        }
        throw new RuntimeException("Expected NoSuchElementException");
    }

    static void testEmptyEnumerationThrows() throws Exception {
        Enumeration<URL> e = new EmptyLoader().getResources(RESOURCE);
        if (e.hasMoreElements()) {
            throw new RuntimeException("Expected no resources");
        }
        expectNoSuchElement(e);
    }

    static void testSingleElementThenThrows() throws Exception {
        URL expected = new URL("file:/witup/resource.txt");
        Enumeration<URL> e = new SingleUrlLoader(expected).getResources(RESOURCE);
        if (!e.hasMoreElements()) {
            throw new RuntimeException("Expected one resource");
        }
        URL actual = e.nextElement();
        if (!expected.equals(actual)) {
            throw new RuntimeException("Unexpected URL: " + actual);
        }
        if (e.hasMoreElements()) {
            throw new RuntimeException("Expected enumeration to be exhausted");
        }
        expectNoSuchElement(e);
    }

    public static void main(String[] args) throws Exception {
        testEmptyEnumerationThrows();
        testSingleElementThenThrows();
    }
}
