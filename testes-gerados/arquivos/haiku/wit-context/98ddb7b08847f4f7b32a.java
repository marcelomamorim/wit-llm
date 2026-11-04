/*
 * @test
 * @summary Verifies that Iterator.remove() obtained from Collections.unmodifiableCollection throws UnsupportedOperationException
 * @run main CollectionsWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CollectionsWitupTest {

    public static void main(String[] args) {
        List<String> base = new ArrayList<>(Arrays.asList("a", "b"));
        Collection<String> view = Collections.unmodifiableCollection(base);

        Iterator<String> beforeNext = view.iterator();
        expectRemoveUnsupported(beforeNext, "remove before next");

        Iterator<String> afterNext = view.iterator();
        afterNext.next();
        expectRemoveUnsupported(afterNext, "remove after next");

        if (base.size() != 2 || !base.contains("a") || !base.contains("b")) {
            throw new RuntimeException("underlying collection was modified");
        }

        Set<String> set = new HashSet<>(Arrays.asList("x"));
        Collection<String> setView = Collections.unmodifiableCollection(set);
        Iterator<String> setIt = setView.iterator();
        setIt.next();
        expectRemoveUnsupported(setIt, "set view remove after next");
        if (set.size() != 1 || !set.contains("x")) {
            throw new RuntimeException("underlying set was modified");
        }

        Collection<String> emptyView = Collections.unmodifiableCollection(new ArrayList<String>());
        expectRemoveUnsupported(emptyView.iterator(), "empty view remove");
    }

    private static void expectRemoveUnsupported(Iterator<?> it, String label) {
        try {
            it.remove();
        } catch (UnsupportedOperationException expected) {
            return;
        }
        throw new RuntimeException(label + ": expected UnsupportedOperationException");
    }
}
