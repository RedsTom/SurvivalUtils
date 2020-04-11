package fr.novalya.survival_utils.commands.sut.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("all")
public abstract class ArrayBuilder<F, T> {

    public List<T> build(Collection<F> list) {
        List<T> arrayList = new ArrayList<>();
        for (F obj : list) arrayList.add(apply(obj));
        return arrayList;
    }

    public List<T> build(F... list) {
        return build(Arrays.asList(list));
    }

    public abstract T apply(F obj);
}
