/*
 * @test
 * @summary Verify that addAll on immutable List and Set instances throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testListAddAllNonEmptyArgument();
        testListAddAllEmptyArgument();
        testListAddAllEmptyCollection();
        testListAddAllNullArgumentThrowsUoe();
        testSetAddAllThrowsUoe();
        testSetAddAllEmptyArgumentThrowsUoe();
    }

    static void testListAddAllNonEmptyArgument() {
        List<Integer> imm = List.of(1, 2, 3);
        Collection<Integer> arg = List.of(4, 5);
        expectUoe("List.of addAll non-empty", () -> imm.addAll(arg));
        check(imm.size() == 3, "list size changed after failed addAll");
        check(imm.get(0) == 1 && imm.get(2) == 3, "list contents changed after failed addAll");
    }

    static void testListAddAllEmptyArgument() {
        List<Integer> imm = List.of(1, 2, 3);
        Collection<Integer> arg = new ArrayList<>();
        expectUoe("List.of addAll empty argument", () -> imm.addAll(arg));
        check(imm.size() == 3, "list size changed after empty addAll");
    }

    static void testListAddAllEmptyCollection() {
        List<Integer> imm = List.of();
        Collection<Integer> arg = List.of(7);
        expectUoe("empty List.of addAll", () -> imm.addAll(arg));
        check(imm.isEmpty(), "empty list changed after failed addAll");
    }

    static void testListAddAllNullArgumentThrowsUoe() {
        List<Integer> imm = List.of(1);
        expectUoe("List.of addAll null argument", () -> imm.addAll(null));
    }

    static void testSetAddAllThrowsUoe() {
        Set<Integer> imm = Set.of(1, 2);
        Collection<Integer> arg = List.of(3);
        expectUoe("Set.of addAll", () -> imm.addAll(arg));
        check(imm.size() == 2, "set size changed after failed addAll");
        check(imm.contains(1) && imm.contains(2), "set contents changed after failed addAll");
    }

    static void testSetAddAllEmptyArgumentThrowsUoe() {
        Set<Integer> imm = Set.of(1);
        Collection<Integer> arg = new ArrayList<>();
        expectUoe("Set.of addAll empty argument", () -> imm.addAll(arg));
    }

    static void expectUoe(String name, Runnable r) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException("Expected UnsupportedOperationException: " + name);
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
