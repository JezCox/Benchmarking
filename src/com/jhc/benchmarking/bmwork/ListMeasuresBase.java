
package com.jhc.benchmarking.bmwork;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

abstract class ListMeasuresBase implements Measure, BMListTests {
    
   List<String> _listUnderBenchmark;
    
    @Override
    public List<String> performTenElementBenchmarks() {
        return benchmarkForN(10);
    }

    @Override
    public List<String> performHundredElementBenchmarks() {
        return benchmarkForN(100);
    }

    @Override
    public List<String> performThousandElementBenchmarks() {
        return benchmarkForN(1000);
    }

    @Override
    public List<String> performTenThousandElementBenchmarks() {
        return benchmarkForN(10000);
    }

    @Override
    public List<String> performMillionElementBenchmarks() {
        return benchmarkForN(1_000_000);
    }

    @Override
    public List<String> performHundredMillionElementBenchmark() {
        return benchmarkForN(100_000_000);
    }

    private List<String> benchmarkForN(long numElements) {
        
        _listUnderBenchmark.clear();
        
        List<String> responses = new ArrayList<>();
        long[] vals = LongStream.rangeClosed(1, numElements).toArray();
        
        // we can't assure an ArrayList<> by streaming :-( !!!...
//        List<Long> longsList = Arrays.stream(vals).boxed().collect(Collectors.toList());
//        _listUnderBenchmark = longsList.stream()
//                .map(l -> Long.toString(l))
//                .collect(Collectors.toList());
        
        // ...is there a better way (re above) ??
        for (long l: vals) {
            _listUnderBenchmark.add(Long.toString(l));
        }
        
        responses.add(atFirst());
        responses.add(atNth());
        responses.add(atMiddle());
        responses.add(indexOfFirst());
        responses.add(indexOfNth());
        responses.add(indexOfMiddle());
        responses.add(indexOfRandomth());
        responses.add(streamFilterFirstHalf());
        return responses;
    }
    
    
    @Override
    public String atFirst() {
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        String at0 = _listUnderBenchmark.get(0);
        return "get at 0 took " + nsw.Stop() + "ns";
    }

    @Override
    public String atNth() {
        int size = _listUnderBenchmark.size();
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        String atNth = _listUnderBenchmark.get(size-1);
        return "get at nth took " + nsw.Stop() + "ns";
    }

    @Override
    public String atMiddle() {
        int size = _listUnderBenchmark.size();
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        String atMiddle = _listUnderBenchmark.get(size/2 - 1);
        return "get at middle took " + nsw.Stop() + "ns";
    }

    @Override
    public String indexOfFirst() {
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        int indexOf = _listUnderBenchmark.indexOf("0");
        return "indexOf '0' took " + nsw.Stop() + "ns";
    }

    @Override
    public String indexOfNth() {
        int size = _listUnderBenchmark.size();
        String nthElement = Integer.toString(size-1);
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        int indexOf = _listUnderBenchmark.indexOf(nthElement);
        return "indexOf '" + nthElement + "' took " + nsw.Stop() + "ns";
    }

    @Override
    public String indexOfMiddle() {
        int size = _listUnderBenchmark.size();
        String nthElement = Integer.toString(size/2 - 1);
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        int indexOf = _listUnderBenchmark.indexOf(nthElement);
        return "indexOf '" + nthElement + "' took " + nsw.Stop() + "ns";
    }

    @Override
    public String indexOfRandomth() {
        int size = _listUnderBenchmark.size();
        int random = (int)(Math.random()*size-1);
        String randomthElement = Integer.toString(random);
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        int indexOf = _listUnderBenchmark.indexOf(randomthElement);
        return "indexOf '" + randomthElement + "'(random) took " + nsw.Stop() + "ns";
    }

    @Override
    public String streamFilterFirstHalf() {
        int listSize = _listUnderBenchmark.size();
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        List<String> firstHalf = _listUnderBenchmark.stream()
                .filter(el -> Integer.parseInt(el) < listSize/2)
                .collect(Collectors.toList());
        return "Stream filter(&collect) for " + listSize/2 + "elements took " + nsw.Stop() + "ns";
    }
        
}
