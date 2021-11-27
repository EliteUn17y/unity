package me.eliteun17y.unity.util.math;

public class Vector3<T> {
    public T a;
    public T b;

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

    public T c;

    public Vector3(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
