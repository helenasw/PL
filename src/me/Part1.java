package me;

import java.util.ArrayList;

public class Part1 {

    public static void main(String[] args) {
        test();
    }

    static void insertIntoMyList(A z, MyList<? super A> L) {
        L.add(z);
    }

    public static void test() {
        MyList<A> m1 = new MyList<A>(new A(0));
        MyList<A> m2 = new MyList<A>(new A(0));
        for(Integer i = 1; i <= 5; i++) {
            insertIntoMyList(new A(i),m1);
            insertIntoMyList(new B(i),m2);
        }

        insertIntoMyList(new A(6),m2);
        System.out.println("m1 = " + m1);
        System.out.println("m2 = " + m2);
        int result = m1.compareTo(m2);
        String s = (result < 0) ? "less than" : (result == 0) ? "equal to" : "grreater than";
        System.out.println("m1 is " + s + " m2");
        System.out.println("m1 + m2 = " + m1.plus(m2));
    }
}

interface Addable<T> {
    T plus(T t);
}

class MyList<T extends Addable<T> & Comparable<T>> extends ArrayList<T> implements  Addable<MyList<T>>, Comparable<MyList<T>> {

    T z;

    public MyList(T z) {
        this.z = z;
    }

    @Override
    public int compareTo(MyList<T> o) {
        return this.sum().compareTo(o.sum());
    }

    T sum() {
        if (this.size() == 0)
            return z;
        T sum = this.get(0);
        for (int i = 1; i < this.size(); i ++)
            sum = sum.plus(this.get(i));
        return sum;
    }

    @Override
    public String toString() {
        String result = "[ ";
        for (T t : this)
            result += t.toString() + " ";
        return  result + "]";
    }

    @Override
    public MyList<T> plus(MyList<T> ts) {
        MyList<T> sum = new MyList<T>(z);
        sum.addAll(this);
        sum.addAll(ts);
        return sum;
    }

}

class A implements Addable<A>, Comparable<A> {

    Integer x;

    public A(Integer x) {
        this.x = x;
    }

    @Override
    public A plus(A a) {
        return new A(x + a.x);
    }

    @Override
    public int compareTo(A o) {
        return x.compareTo(o.x);
    }

    @Override
    public String toString() {
        return "A:" + x;
    }
}

class B extends A {

    public B(Integer x) {
        super(x);
    }

    public String toString() {
        return "B:" + x;
    }

}