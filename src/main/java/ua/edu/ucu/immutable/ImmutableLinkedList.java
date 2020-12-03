package ua.edu.ucu.immutable;


import java.util.Arrays;
import java.util.InputMismatchException;

public class ImmutableLinkedList implements ImmutableList {
    private Node head;
    private int size;

    public ImmutableLinkedList() {
        head = null;
        size = 0;
    }

    public ImmutableLinkedList(Object[] c) {
        checkInput(c);
        if (c.length >= 1) {
            size = c.length;
            head = new Node(c[0], null);
            Node curr = head;
            for (int i = 1; i < c.length; i++) {
                curr.next = new Node(c[i], null);
                curr = curr.next;
            }
        }
    }

    static class Node {
        private Object value;
        private Node next;

        Node(Object val, Node n) {
            value = val;
            next = n;
        }
    }

    public ImmutableLinkedList copy(ImmutableLinkedList lst) {
        if (!isEmpty()) {
            ImmutableLinkedList newImmLinkedList =
                    new ImmutableLinkedList(lst.toArray());
            return newImmLinkedList;
        } else {
            return new ImmutableLinkedList();
        }
    }

    public void checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index!");
        }
    }

    public void checkInput(Object[] arr) {
        for (int j = 0; j < arr.length; j++) {
            if (arr[j] == null) {
                throw new InputMismatchException("Input is incorrect!");
            }
        }
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return add(size, e);
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        return addAll(index, new Object[]{e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(size, c);
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        checkInput(c);
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index!");
        }

        ImmutableLinkedList newImmLinkedList = copy(this);

        if (index == 0) {
            Node toLink = newImmLinkedList.head;
            newImmLinkedList.head = new Node(c[0], null);
            Node curr = newImmLinkedList.head;
            newImmLinkedList.size = newImmLinkedList.size + 1;

            for (int i = 1; i < c.length; i++) {
                curr.next = new Node(c[i], null);
                curr = curr.next;
                newImmLinkedList.size = newImmLinkedList.size + 1;
            }
            curr.next = toLink;

        } else {
            Node curr = newImmLinkedList.head;
            int temp = 1;
            while (temp < index) {
                if (curr.next != null) {
                    curr = curr.next;
                }
                temp = temp + 1;
            }

            Node toLink = curr.next;

            for (int i = 0; i < c.length; i++) {
                curr.next = new Node(c[i], null);
                curr = curr.next;
                newImmLinkedList.size = newImmLinkedList.size + 1;
            }
            curr.next = toLink;
        }

        return newImmLinkedList;

    }


    @Override
    public Object get(int index) {
        checkIndex(index);

        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }

        return curr.value;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        checkIndex(index);

        ImmutableLinkedList newImmLinkedList = copy(this);

        Node curr = newImmLinkedList.head;
        if (index == 0) {
            newImmLinkedList.head = curr.next;
        } else {
            for (int i = 0; i < index - 1; i++) {
                curr = curr.next;
            }
            curr.next = curr.next.next;
        }

        newImmLinkedList.size = newImmLinkedList.size - 1;
        return newImmLinkedList;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        checkIndex(index);
        checkInput(new Object[]{e});

        ImmutableLinkedList newImmLinkedList = copy(this);

        checkIndex(index);

        Node curr = newImmLinkedList.head;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        curr.value = e;
        return newImmLinkedList;
    }

    @Override
    public int indexOf(Object e) {
        Node curr = head;
        for (int i = 0; i < size; i++) {
            if (curr.value.equals(e)) {
                return i;
            }
            curr = curr.next;
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Object[] toArray() {
        if (head != null) {
            Object[] arr = new Object[size];
            arr[0] = head.value;
            Node curr = head;
            for (int i = 1; i < size; i++) {
                arr[i] = curr.next.value;
                curr = curr.next;
            }
            return arr;
        } else {
            return new Object[]{};
        }
    }

    @Override
    public String toString() {
        String str = Arrays.toString(toArray());
        return str.substring(1, str.length() - 1);
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(size - 1);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(size - 1);
    }

}


