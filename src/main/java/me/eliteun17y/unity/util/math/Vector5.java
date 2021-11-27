package me.eliteun17y.unity.util.math;

public class Vector5<T> {
    public T a;
    public T b;
    public T c;
    public T d;
    public T e;

    public T getA() {
        return a;
    }

    public void setA(T a) {
        this.a = a;
    }

    public T getB() {
        return b;
    }

    public void setB(T b) {
        this.b = b;
    }

    public T getC() {
        return c;
    }

    public void setC(T c) {
        this.c = c;
    }

    public T getD() {
        return d;
    }

    public void setD(T d) {
        this.d = d;
    }

    public T getE() {
        return e;
    }

    public void setE(T e) {
        this.e = e;
    }

    public Vector5(T a, T b, T c, T d, T e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }
}
