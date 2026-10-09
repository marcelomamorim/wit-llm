/* @test    @summary Test adding an element at a specific index in AbstractList implementation    @run main AbstractListWitupTest_P1bf0cee4a869 */

import java.util.AbstractList;

default class ConcreteList<E> extends AbstractList<E> {
    private final java.util.List<E> list = new java.util.ArrayList<>();

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
    }
}

public class AbstractListWitupTest_P1bf0cee4a869 {
    public static void main(String[] args) {
        ConcreteList<String> list = new ConcreteList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add(1, "inserted");
        assert list.size() == 4 : "Size should be 4";
        assert list.get(0).equals("one") : "First element should be 'one'";
        assert list.get(1).equals("inserted") : "Second element should be 'inserted'";
        assert list.get(2).equals("two") : "Third element should be 'two'";
        assert list.get(3).equals("three") : "Fourth element should be 'three'";
        System.out.println("Test passed.");
    }
}
