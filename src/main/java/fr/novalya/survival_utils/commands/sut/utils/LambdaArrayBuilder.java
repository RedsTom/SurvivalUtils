package fr.novalya.survival_utils.commands.sut.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class LambdaArrayBuilder<F, T> {

    private Function<F, T> function;

    public LambdaArrayBuilder(Function<F, T> function) {
        this.function = function;
    }

    public List<T> build(Collection<F> list) {
        List<T> arrayList = new ArrayList<>();
        for (F obj : list) arrayList.add(function.apply(obj));
        return arrayList;
    }

    @SafeVarargs
    public final List<T> build(F... list) {
        return build(Arrays.asList(list));
    }

}