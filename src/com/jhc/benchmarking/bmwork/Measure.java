
package com.jhc.benchmarking.bmwork;

import java.util.List;

public interface Measure {
    List<String> performTenElementBenchmarks();
    List<String> performHundredElementBenchmarks();
    List<String> performThousandElementBenchmarks();
    List<String> performTenThousandElementBenchmarks();
    List<String> performMillionElementBenchmarks();
    List<String> performHundredMillionElementBenchmark();
}
