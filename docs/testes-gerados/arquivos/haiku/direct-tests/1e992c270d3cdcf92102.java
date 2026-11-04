/*
 * @test
 * @summary Verify that remove(Object) on immutable List and Set instances throws UnsupportedOperationException and leaves contents unchanged
 * @run main ImmutableCollectionsWitupTest
 */

import java.util.List;
import java.util.Set;

public class ImmutableCollectionsWitupTest {

    public static void main(String[] args) {
        testListRemovePresentElement();
        testListRemoveAbsentElement();
        testListRemoveNull();
        testSetRemovePresentElement();
        testSetRemoveAbsentElement();
    }

    static void testListRemovePresentElement() {
        List<Integer> list = List.of(1, 2, 3);
        expectUOE(() -> list.remove(Integer.valueOf(1)), "List.remove present element");
        check(list.size() == 3, "list size changed after failed remove");
        check(list.contains(1), "element removed from immutable list");
    }

    static void testListRemoveAbsentElement() {
        List<Integer> list = List.of(1, 2, 3);
        expectUOE(() -> list.remove(Integer.valueOf(42)), "List.remove absent element");
        check(list.size() == 3, "list size changed after remove of absent element");
    }

    static void testListRemoveNull() {
        List<Integer> list = List.of(1, 2, 3);
        expectUOE(() -> list.remove((Object) null), "List.remove null");
        check(list.size() == 3, "list size changed after remove(null)");
    }

    static void testSetRemovePresentElement() {
        Set<String> set = Set.of("a", "b");
        expectUOE(() -> set.remove("a"), "Set.remove present element");
        check(set.size() == 2, "set size changed after failed remove");
        check(set.contains("a"), "element removed from immutable set");
    }

    static void testSetRemoveAbsentElement() {
        Set<String> set = Set.of("a", "b");
        expectUOE(() -> set.remove("z"), "Set.remove absent element");
        check(set.size() == 2, "set size changed after remove of absent element");
    }

    static void expectUOE(Runnable r, String what) {
        try {
            r.run();
        } catch (UnsupportedOperationException e) {
            return;
        }
        throw new RuntimeException(what + ": expected UnsupportedOperationException");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }
}
