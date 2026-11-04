/*
 * @test
 * @summary Test java.nio.file.FileTreeIterator.next() method behavior
 * @run main FileTreeIteratorWitupTest
 */

import java.nio.file.FileTreeIterator;
import java.nio.file.FileTreeWalker;
import java.nio.file.FileVisitOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileTreeWalker.Event;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Collections;
import java.util.Iterator;

public class FileTreeIteratorWitupTest {

    public static void main(String[] args) throws Exception {
        testNextReturnsEventAndAdvances();
        testNextThrowsNoSuchElementExceptionWhenNoMoreElements();
        testNextThrowsIllegalStateExceptionWhenClosed();
    }

    private static void testNextReturnsEventAndAdvances() throws IOException {
        // Use current directory as start path
        Path start = Paths.get(".");
        FileTreeIterator it = new FileTreeIterator(start, 1, new FileVisitOption[0]);
        try {
            // The first call to next() should return an Event
            Event ev1 = it.next();
            if (ev1 == null) {
                throw new AssertionError("next() returned null event");
            }
            // The second call to next() should return another Event or throw NoSuchElementException
            boolean secondReturned = false;
            try {
                Event ev2 = it.next();
                if (ev2 == null) {
                    throw new AssertionError("second next() returned null event");
                }
                secondReturned = true;
            } catch (NoSuchElementException ignore) {
                // acceptable if no more elements
            }
            // If secondReturned is true, ev2 should be different from ev1
            if (secondReturned) {
                // no direct equals, but events should not be the same object
                // (not strictly required, but reasonable check)
                // no assert here, just sanity
            }
        } finally {
            it.close();
        }
    }

    private static void testNextThrowsNoSuchElementExceptionWhenNoMoreElements() throws IOException {
        // Create an iterator with maxDepth=0 so it only returns the start path
        Path start = Paths.get(".");
        FileTreeIterator it = new FileTreeIterator(start, 0, new FileVisitOption[0]);
        try {
            // Consume the only element
            Event ev = it.next();
            if (ev == null) {
                throw new AssertionError("next() returned null event");
            }
            // Next call should throw NoSuchElementException
            boolean threw = false;
            try {
                it.next();
            } catch (NoSuchElementException e) {
                threw = true;
            }
            if (!threw) {
                throw new AssertionError("next() did not throw NoSuchElementException when no more elements");
            }
        } finally {
            it.close();
        }
    }

    private static void testNextThrowsIllegalStateExceptionWhenClosed() throws IOException {
        Path start = Paths.get(".");
        FileTreeIterator it = new FileTreeIterator(start, 1, new FileVisitOption[0]);
        it.close();
        boolean threw = false;
        try {
            it.next();
        } catch (IllegalStateException e) {
            threw = true;
        }
        if (!threw) {
            throw new AssertionError("next() did not throw IllegalStateException after close");
        }
    }
}
