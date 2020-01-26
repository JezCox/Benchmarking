package com.jhc.benchmarking.bmwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public final class LinkedListMeasure extends ListMeasuresBase {

    public LinkedListMeasure() {
        _listUnderBenchmark = new LinkedList<>();
    }

    @Override
    void populateListFromArray(long[] vals) {
        _listUnderBenchmark = Arrays.stream(vals)
                    .boxed()
                    .collect(
                        () -> new LinkedList<>(),
                        (list, element) -> list.add(element.toString()),
                        (listA, listB) -> listA.addAll(listB) 
                            );
    }

    
}
