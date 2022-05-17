package com.example.team.team.Bean;

public class LinkedList<E> {
    private transient int size = 0;
    private transient Node<E> first;
    private transient Node<E> last;

    //内部类
    private static class Node<E> {
        E item; //当前节点中存储的对象
        Node<E> next;   //下一个节点
        Node<E> prev;   //上一个节点

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    //长度
    public int Size() {
        return size;
    }

    //空判断
    public boolean isEmpty() {
        return size == 0;
    }

    //越界判断
    public void rangeCheck(int index) {
        if (index < 0 || index >= size) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //添加最后一个元素
    public void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
        //modCount++;
    }

    //将元素添加到指定节点的----前面
    public void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;
        //modCount++;
    }

    //返回索引值对应的结点
    public Node<E> node(int index) {
        // assert isElementIndex(index);
        if (index < (size >> 1)) {//从前往后找
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {//从后往前找
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    //获取指定位置的元素
    public E get(int index) {
        rangeCheck(index);
        return node(index).item;
    }

    //断开该结点，并返回该结点的元素
    public E unlink(Node<E> x) {
        // assert x != null;
        final E element = x.item;
        final Node<E> next = x.next;
        final Node<E> prev = x.prev;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            x.prev = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
            x.next = null;
        }

        x.item = null;
        size--;
        //modCount++;
        return element;
    }

    //添加元素
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    //添加元素到指定位置(在改位置之前)
    public void add(int index, E element) {
        rangeCheck(index);
        if (index == size)//判断是否是最后一个元素
            linkLast(element);
        else
            linkBefore(element, node(index));//特别注意这里的node(index)
    }
    //删除跟指定元素相等的第一个元素
    public boolean remove(Object o) {
        if (o == null) {
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);
                    return true;
                }
            }
        } else {
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) { //使用.equals方法
                    unlink(x);
                    return true;
                }
            }
        }
        return false;
    }

    //删除指定下标处的元素
    public E remove(int index) {
        rangeCheck(index);
        return unlink(node(index)); //寻找node的位置。同add方法
    }

}
