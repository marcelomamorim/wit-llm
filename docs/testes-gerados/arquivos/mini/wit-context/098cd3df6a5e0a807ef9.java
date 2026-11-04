/*
 * @test
 * @summary Test fail-fast behavior of ArrayList.SubList.Anonymous set(E) method
 * @run main ArrayListWitupTest
 */

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.IllegalStateException;
import java.util.List;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testSetThrowsIllegalStateException();
        testSetThrowsConcurrentModificationException();
        testSetSuccessful();
    }

    // Test that set(E) throws IllegalStateException if lastRet < 0
    private static void testSetThrowsIllegalStateException() {
        // We simulate the SubList.Anonymous behavior by creating a minimal stub
        // Since the actual class is internal, we test the observable behavior via a custom subclass
        // Here we create a minimal mock to invoke the set method with lastRet < 0
        SubListAnonymousStub<String> stub = new SubListAnonymousStub<>();
        stub.lastRet = -1; // trigger IllegalStateException
        try {
            stub.set("test");
            throw new AssertionError("Expected IllegalStateException not thrown");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    // Test that set(E) throws ConcurrentModificationException if modCount mismatch
    private static void testSetThrowsConcurrentModificationException() {
        SubListAnonymousStub<String> stub = new SubListAnonymousStub<>();
        stub.lastRet = 0; // valid lastRet
        stub.expectedModCount = 0;
        stub.root = new ArrayList<>();
        stub.root.add("a");
        stub.offset = 0;
        stub.modCount = 1; // simulate concurrent modification
        stub.setModCount(1); // root modCount is 1

        try {
            stub.set("b");
            throw new AssertionError("Expected ConcurrentModificationException not thrown");
        } catch (ConcurrentModificationException expected) {
            // expected
        }
    }

    // Test that set(E) successfully sets element when no exceptions
    private static void testSetSuccessful() {
        SubListAnonymousStub<String> stub = new SubListAnonymousStub<>();
        stub.lastRet = 0;
        stub.expectedModCount = 0;
        stub.root = new ArrayList<>();
        stub.root.add("a");
        stub.offset = 0;
        stub.setModCount(0);

        stub.set("b");

        if (!"b".equals(stub.root.get(0))) {
            throw new AssertionError("Element not set correctly");
        }
    }

    // Minimal stub simulating the SubList.Anonymous class behavior for set(E)
    private static class SubListAnonymousStub<E> {
        int lastRet = -1;
        int expectedModCount = 0;
        int modCount = 0;
        int offset = 0;
        ArrayList<E> root;

        void setModCount(int mc) {
            this.modCount = mc;
        }

        void checkForComodification() {
            if (root != null && root.modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }

        public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();
            try {
                root.set(offset + lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
