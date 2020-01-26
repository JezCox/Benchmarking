
package com.jhc.benchmarking.bmwork;

import java.util.ArrayList;
import java.util.Arrays;

public final class ArrayListMeasure extends ListMeasuresBase {
    
    public ArrayListMeasure() {
        _listUnderBenchmark = new ArrayList<>();
    }

    @Override
    void populateListFromArray(long[] vals) {
        _listUnderBenchmark = Arrays.stream(vals)
                    .boxed()
                    .collect(
                        () -> new ArrayList<>(),
                        (list, element) -> list.add(element.toString()),
                        (listA, listB) -> listA.addAll(listB) 
                            );
    }
    
}
