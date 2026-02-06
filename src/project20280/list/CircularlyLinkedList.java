package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CircularlyLinkedList<E> implements List<E> {

    private class Node<T> {
        private final T data;
        private Node<T> next;

        public Node(T e, Node<T> n) {
            data = e;
            next = n;
        }

        public T getData() {
            return data;
        }

        public void setNext(Node<T> n) {
            next = n;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    private Node<E> tail = null;
    private int size = 0;

    public CircularlyLinkedList() {

    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        }

        Node<E> current = (tail == null) ? null : tail.next;
        for (int j = 0; j < i; j++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public void add(int i, E e) {
        if (i < 0 || i > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        }

        if (i == 0) {
            addFirst(e);
        } else if (i == size) {
            addLast(e);
        } else {
            Node<E> current = tail.next;
            for (int j = 0; j < i - 1; j++) {
                current = current.next;
            }
            Node<E> newNode = new Node<>(e, current.next);
            current.setNext(newNode);
            if (current == tail) {
                tail = newNode;
            }
            size++;
        }
    }

    @Override
    public E remove(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + i);
        }

        if (i == 0) {
            return removeFirst();
        } else if (i == size - 1) {
            return removeLast();
        } else {
            Node<E> current = tail.next;
            for (int j = 0; j < i - 1; j++) {
                current = current.next;
            }
            Node<E> nodeToRemove = current.next;
            current.setNext(nodeToRemove.next);
            if (nodeToRemove == tail) {
                tail = current;
            }
            size--;
            return nodeToRemove.data;
        }
    }

    public void rotate() {
        if (tail != null) {
            tail = tail.next;
        }
    }

    private class CircularlyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) tail;
        boolean firstPass = true;

        @Override
        public boolean hasNext() {
            if (tail == null) return false;
            return firstPass || curr != tail;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (firstPass) {
                //curr = tail.next;
                firstPass = false;
            } else {
                curr = curr.next;
            }
            E res = curr.data;
            return res;
        }

    }

    @Override
    public Iterator<E> iterator() {
        return new CircularlyLinkedListIterator<E>();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        Node<E> head = tail.next;
        if (head == tail) {
            tail = null;
        } else {
            tail.setNext(head.next);
        }
        size--;
        return head.data;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        if (size == 1) {
            E data = tail.data;
            tail = null;
            size--;
            return data;
        }

        Node<E> current = tail.next;
        while (current.next != tail) {
            current = current.next;
        }

        E data = tail.data;
        current.setNext(tail.next);
        tail = current;
        size--;
        return data;
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, null);
        if (isEmpty()) {
            tail = newNode;
            tail.setNext(tail);
        } else {
            newNode.setNext(tail.next);
            tail.setNext(newNode);
        }
        size++;
    }

    @Override
    public void addLast(E e) {
        addFirst(e);
        tail = tail.next;
    }

    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = tail.next;
        do {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != tail.next) {
                sb.append(", ");
            }
        } while (curr != tail.next);
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        CircularlyLinkedList<Integer> ll = new CircularlyLinkedList<Integer>();
        for (int i = 10; i < 20; ++i) {
            ll.addLast(i);
        }

        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        ll.rotate();
        System.out.println(ll);

        ll.removeFirst();
        ll.rotate();
        System.out.println(ll);

        ll.removeLast();
        ll.rotate();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }

    }
}