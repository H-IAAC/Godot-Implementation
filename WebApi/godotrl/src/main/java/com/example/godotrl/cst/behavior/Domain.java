package com.example.godotrl.cst.behavior;

public final class Domain<T extends Number & Comparable<T>> implements Comparable<Domain<?>> {
    T val;

    public Domain(T val) {
        this.val = val;
    }

    @Override
    public int hashCode(){
        return this.val !=null ? this.val.hashCode() : 0;
    }

    @Override
    public int compareTo(Domain<?> state) {
        return doubleValue().compareTo(state.doubleValue());
        // return val.compareTo((T) state.val);

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Domain)) return false;
        return this.val != null && val.equals(((Domain<?>) obj).val);
    }

    public Integer intValue() {
        return (Integer) this.val;
    }

    public Long longValue() {
        return (Long) this.val;
    }

    public Float floatValue() {
        return (Float) this.val;
    }

    public Double doubleValue() {
        return (Double) this.val;
    }

    @Override
    public String toString() {
        return this.val.toString();
    }
}
