package ru.netology.entity;

public enum Header {
    IP_ADDRESS("x-real-ip");

    public final String value;

    Header(String value) {
        this.value = value;
    }
}
