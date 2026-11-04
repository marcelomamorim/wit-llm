/*
 * @test
 * @summary Testa o método forEach de java.util.Collections para comportamento com action null
 * @run main CollectionsWitupTest
 */

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class CollectionsWitupTest {
    public static void main(String[] args) {
        testForEachWithNullAction();
        testForEachWithValidAction();
    }

    static void testForEachWithNullAction() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        try {
            Collections.forEach(list, null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testForEachWithValidAction() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        List<Integer> result = new ArrayList<>();
        java.util.function.Consumer<Integer> consumer = (Integer x) -> result.add(x * 2);
        try {
            Collections.forEach(list, consumer);
            if (result.size() == 3 && result.get(0) == 2 && result.get(1) == 4 && result.get(2) == 6) {
                System.out.println("PASS: forEach applied consumer correctly");
            } else {
                System.out.println("FAIL: forEach did not apply consumer correctly, result: " + result);
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
