package project20280.stacksqueues;

import project20280.interfaces.Stack;

public class ArrayStack<E> implements Stack<E> {

    public static final int CAPACITY = 1000;
    private E[] data;
    private int top = -1;

    public ArrayStack(int capacity) {
        // TODO
        data = (E[]) new Object[capacity];

    }

    public ArrayStack() {
        this(CAPACITY);
    }

    @Override
    public int size() {
        // TODO
        return top + 1;
    }

    @Override
    public boolean isEmpty() {
        // TODO
        return top == -1;
    }

    @Override
    public void push(E e) {
        // TODO
        data[++top] = e;
    }

    @Override
    public E top() {
        // TODO
        return data[top];
    }

    @Override
    public E pop() {
        // TODO
        E e = data[top];
        top--;
        return e;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = top; i >= 0; --i) {
            sb.append(data[i]);
            if (i != 0) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Stack<Integer> stk = new ArrayStack<>();
        stk.push(1);
        stk.push(2);
        stk.push(3);
        System.out.println("stk: " + stk);
        stk.pop();
        System.out.println("stk: " + stk);
    }
}