package com.example.eautosalon.data;

public class SpinnerKeyValue {
    public String value;
    public int key;

    public SpinnerKeyValue(String _value, int _key) {
        value = _value;
        key = _key;
    }

    @Override
    public String toString() {
        return value;
    }
}
