/*
 * @test
 * @summary Test deterministic behavior of FileTreeIterator.next() including exception on closed iterator
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
        testNextThrowsIllegalStateExceptionWhenClosed();
        testNextThrowsNoSuchElementExceptionWhenNoMoreElements();
        testNextReturnsEventWhenAvailable();
    }

    private static void testNextThrowsIllegalStateExceptionWhenClosed() throws IOException {
        Path start = Paths.get(".");
        FileTreeIterator it = new FileTreeIterator(start, 0, new FileVisitOption[0]);
        it.close();
        try {
            it.next();
            throw new RuntimeException("Expected IllegalStateException when calling next() on closed iterator");
        } catch (IllegalStateException expected) {
            // expected
        }
    }

    private static void testNextThrowsNoSuchElementExceptionWhenNoMoreElements() throws IOException {
        Path start = Paths.get(".");
        FileTreeIterator it = new FileTreeIterator(start, 0, new FileVisitOption[0]);
        // Exhaust the iterator
        while (it.hasNext()) {
            it.next();
        }
        try {
            it.next();
            throw new RuntimeException("Expected NoSuchElementException when calling next() with no elements");
        } catch (NoSuchElementException expected) {
            // expected
        } finally {
            it.close();
        }
    }

    private static void testNextReturnsEventWhenAvailable() throws IOException {
        Path start = Paths.get(".");
        FileTreeIterator it = new FileTreeIterator(start, 0, new FileVisitOption[0]);
        try {
            if (it.hasNext()) {
                Event ev = it.next();
                if (ev == null) {
                    throw new RuntimeException("next() returned null event");
                }
                // Check that event type is ENTRY or START_DIRECTORY as per constructor assertion
                FileTreeWalker.EventType type = ev.type();
                if (type != FileTreeWalker.EventType.ENTRY && type != FileTreeWalker.EventType.START_DIRECTORY) {
                    throw new RuntimeException("Event type is not ENTRY or START_DIRECTORY");
                }
                if (ev.ioeException() != null) {
                    throw new RuntimeException("Event has IOException unexpectedly");
                }
            } else {
                // It is possible that the directory is empty or inaccessible, so no events
            }
        } finally {
            it.close();
        }
    }
}
