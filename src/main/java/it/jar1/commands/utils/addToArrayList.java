package it.jar1.commands.utils;

import java.util.*;

public class addToArrayList
{
    public ArrayList list;
    public ArrayList elements;
    
    public addToArrayList(final ArrayList list, final Object... elements) {
        this.list = list;
        this.elements.add(elements);
        list.add(elements);
    }
}
