package it.jar1.commands.utils;

import java.util.ArrayList;

public class addToArrayList {
    public ArrayList list;
    public ArrayList elements;
    public addToArrayList(ArrayList list, Object... elements) {
        this.list = list;
        this.elements.add(elements);
        list.add(elements);
    }
}
