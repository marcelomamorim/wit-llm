/*
 * @test
 * @summary Tests StackWalker.getInstance(Set, int) argument validation and basic walking behavior
 * @run main StackWalkerWitupTest
 */

import java.util.EnumSet;
import java.util.Set;

public class StackWalkerWitupTest {
    public static void main(String[] args) {
        testNonPositiveDepthRejected();
        testNullOptionsRejected();
        testPositiveDepthAccepted();
        testEmptyOptionsAccepted();
        testRetainClassReferenceGivesDeclaringClass();
        testWithoutRetainClassReferenceUsesClassName();
    }

    static void testNonPositiveDepthRejected() {
        expectIllegalArgument(0);
        expectIllegalArgument(-1);
    }

    static void expectIllegalArgument(int depth) {
        try {
            StackWalker.getInstance(EnumSet.noneOf(StackWalker.Option.class), depth);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for estimateDepth " + depth);
    }

    static void testNullOptionsRejected() {
        try {
            StackWalker.getInstance((Set<StackWalker.Option>) null, 1);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null options");
    }

    static void testPositiveDepthAccepted() {
        StackWalker walker = StackWalker.getInstance(EnumSet.noneOf(StackWalker.Option.class), 1);
        if (walker == null) {
            throw new RuntimeException("getInstance returned null");
        }
        long count = walker.walk(s -> s.count());
        if (count <= 0) {
            throw new RuntimeException("Expected at least one stack frame, got " + count);
        }
    }

    static void testEmptyOptionsAccepted() {
        StackWalker walker = StackWalker.getInstance(EnumSet.noneOf(StackWalker.Option.class), 10);
        String name = walker.walk(s -> s.findFirst().get().getClassName());
        if (!"StackWalkerWitupTest".equals(name)) {
            throw new RuntimeException("Unexpected top frame class name: " + name);
        }
    }

    static void testRetainClassReferenceGivesDeclaringClass() {
        StackWalker walker = StackWalker.getInstance(
                EnumSet.of(StackWalker.Option.RETAIN_CLASS_REFERENCE), 5);
        Class<?> c = walker.walk(s -> s.findFirst().get().getDeclaringClass());
        if (c != StackWalkerWitupTest.class) {
            throw new RuntimeException("Unexpected declaring class: " + c);
        }
    }

    static void testWithoutRetainClassReferenceUsesClassName() {
        StackWalker walker = StackWalker.getInstance(EnumSet.noneOf(StackWalker.Option.class), 5);
        boolean unsupported = false;
        try {
            walker.walk(s -> s.findFirst().get().getDeclaringClass());
        } catch (UnsupportedOperationException e) {
            unsupported = true;
        }
        if (!unsupported) {
            throw new RuntimeException("Expected getDeclaringClass to be unsupported without RETAIN_CLASS_REFERENCE");
        }
    }
}
