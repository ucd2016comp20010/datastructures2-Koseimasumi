package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E> {

    private static class Node<E> {

        private final E element;            // reference to the element stored at this node
        private Node<E> next;               // reference to the subsequent node in the list

        /**
         * Creates a node with the given element and next node.
         *
         * @param e the element to be stored
         * @param n reference to a node that should follow the new node
         */
        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        // Accessor methods

        /**
         * Returns the element stored at the node.
         *
         * @return the element stored at the node
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the node that follows this one (or null if no such node).
         *
         * @return the following node
         */
        public Node<E> getNext() {
            return next;
        }

        // Modifier methods

        /**
         * Sets the node's next reference to point to Node n.
         *
         * @param n the node that should follow this one
         */
        public void setNext(Node<E> n) {
            next = n;
        }
    } //----------- end of nested Node class -----------

    /**
     * The head node of the list
     */
    private Node<E> head = null;               // head node of the list (or null if empty)

    /**
     * Number of nodes in the list
     */
    private int size = 0;                      // number of nodes in the list

    public SinglyLinkedList() {
    }              // constructs an initially empty list

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public E get(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        Node<E> current = head;
        for (int i = 0; i < position; i++) {
            current = current.getNext();
        }
        return current.getElement();
    }

    @Override
    public void add(int position, E e) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        if (position == 0) {
            addFirst(e);
        } else {
            Node<E> current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.getNext();
            }
            Node<E> newNode = new Node<>(e, current.getNext());
            current.setNext(newNode);
            size++;
        }
    }

    @Override
    public void addFirst(E e) {
        head = new Node<>(e, head);
        size++;
    }

    @Override
    public void addLast(E e) {
        if (isEmpty()) {
            addFirst(e);
        } else {
            Node<E> current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(new Node<>(e, null));
            size++;
        }
    }

    @Override
    public E remove(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Invalid position: " + position);
        }

        if (position == 0) {
            return removeFirst();
        } else {
            Node<E> current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.getNext();
            }
            Node<E> nodeToRemove = current.getNext();
            current.setNext(nodeToRemove.getNext());
            size--;
            return nodeToRemove.getElement();
        }
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        E element = head.getElement();
        head = head.getNext();
        size--;
        return element;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }

        if (size == 1) {
            return removeFirst();
        }

        Node<E> current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        E element = current.getNext().getElement();
        current.setNext(null);
        size--;
        return element;
    }

    @Override
    public Iterator<E> iterator() {
        return new SinglyLinkedListIterator<>();
    }

    private class SinglyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head;

        @Override
        public boolean hasNext() {
            return curr != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E res = curr.getElement();
            curr = curr.getNext();
            return res;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        while (curr != null) {
            sb.append(curr.getElement());
            if (curr.getNext() != null)
                sb.append(", ");
            curr = curr.getNext();
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
        System.out.println("ll " + ll + " isEmpty: " + ll.isEmpty());

        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addFirst(3);
        ll.addFirst(4);
        ll.addLast(-1);

        System.out.println("List after adds: " + ll);
        System.out.println("Size: " + ll.size());
        System.out.println("Element at position 2: " + ll.get(2));

        ll.remove(2);
        System.out.println("List after removing position 2: " + ll);

        ll.removeFirst();
        System.out.println("List after removeFirst: " + ll);

        ll.removeLast();
        System.out.println("List after removeLast: " + ll);

        // Test iterator
        System.out.print("List elements using iterator: ");
        for (Integer num : ll) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}