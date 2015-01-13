package no.njm.example;

import java.util.ArrayList;
import java.util.List;

class Foo {

    String name;
    List<Bar> bars = new ArrayList<>();

    Foo(String name) {
        this.name = name;
    }
}
