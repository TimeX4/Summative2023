package org.file;

import java.io.Serial;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicFloat extends Number {
    @Serial
    private static final long serialVersionUID = 4992180751166673712L;

    private final AtomicInteger value;

    public AtomicFloat() {
        this(0.0f);
    }

    public AtomicFloat(float value) {
        this.value = new AtomicInteger(i(value));
    }

    public final float get() {
        return f(value.get());
    }

    public final void set(float newValue) {
        value.set(i(newValue));
    }

    public String toString() {
        return Float.toString(get());
    }

    @Override
    public int intValue() {
        return (int) get();
    }

    @Override
    public long longValue() {
        return (long) get();
    }

    @Override
    public float floatValue() {
        return get();
    }

    @Override
    public double doubleValue() {
        return get();
    }

    private static int i(final float f) {
        return Float.floatToIntBits(f);
    }

    private static float f(final int i) {
        return Float.intBitsToFloat(i);
    }

}




