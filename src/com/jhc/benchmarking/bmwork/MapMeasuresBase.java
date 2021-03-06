package com.jhc.benchmarking.bmwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.LongStream;
import java.util.stream.Stream;

abstract class MapMeasuresBase implements Measure, BMMapTests{

    Map<String, Long> _mapUnderBenchmark;

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
    
    private void resetMap(long numElements) {
        _mapUnderBenchmark.clear();
        
        long[] vals = LongStream.rangeClosed(1, numElements).toArray();
        for (long l: vals) {
            _mapUnderBenchmark.put(String.valueOf(l), l);
        }
    }

    private List<String> benchmarkForN(long numElements) {
        
        List<String> responses = new ArrayList<>();
        resetMap(numElements);
        responses.add(getZeroth());
        responses.add(getFirst());
        responses.add(getNth());
        responses.add(getMiddle());
        responses.add(getRandom());
        responses.add(putNewAtEnd());
        responses.add(replaceFirst());
        responses.add(replaceNth());
        responses.add(replaceRandomth());
        responses.add(streamFilterValuesLessThanMedian());
        responses.add(streamSortAndReduceToSum());
        responses.add(streamSortAndCopyToNewMap());
        return responses;
    }
    
    @Override
    public String getZeroth() {
        // which doesn't exist
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
//        Long zerothVal = _mapUnderBenchmark.get("0");   // will be null
        Optional<Long> optZeroth = Optional.ofNullable(_mapUnderBenchmark.get("0"));
        return "getZeroth took " + nsw.Stop() + "ns";
    }

    
    @Override
    public String getFirst() {
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        Long firstVal = _mapUnderBenchmark.get("1");
        return "getFirst took " + nsw.Stop() + "ns";
    }

    @Override
    public String getNth() {
        int size = _mapUnderBenchmark.size();
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        Long nthVal = _mapUnderBenchmark.get(Integer.toString(size-1));
        return "getNth (" +  (size-1) + ") took " + nsw.Stop() + "ns";
    }

    @Override
    public String getMiddle() {
        int size = _mapUnderBenchmark.size();
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        Long midVal = _mapUnderBenchmark.get(Integer.toString(size/2 -1));
        return "getMiddle (" + (size/2-1) + ") took " + nsw.Stop() + "ns";
    }

    @Override
    public String getRandom() {
        int size = _mapUnderBenchmark.size();
        int random = (int)(Math.random()*size-1);
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        Long randomVal = _mapUnderBenchmark.get(Integer.toString(random));
        return "getRandom (" + random + ") took " + nsw.Stop() + "ns";
    }

    @Override
    public String putNewAtEnd() {
        int size = _mapUnderBenchmark.size();
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        long lSize = size + 1;
        _mapUnderBenchmark.put(Long.toString(lSize), lSize);
        return "putNewAtEnd took " + nsw.Stop() + "ns";
    }

    @Override
    public String replaceFirst() {
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        _mapUnderBenchmark.replace("1", 5L);
        return "replaceFirst took " + nsw.Stop() + "ns";
    }

    @Override
    public String replaceNth() {
        int size = _mapUnderBenchmark.size();
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        _mapUnderBenchmark.replace(Integer.toString(size), 5L);
        return "replaceNth(at key:" + size + ") took " + nsw.Stop() + "ns";
    }

    @Override
    public String replaceRandomth() {
        int size = _mapUnderBenchmark.size();
        int random = (int)(Math.random()*size-1);
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        _mapUnderBenchmark.replace(Integer.toString(random), 5L);
        return "replaceRandom(at key:" + random + ")  took " + nsw.Stop() + "ns";
    }

    @Override
    public String streamFilterValuesLessThanMedian() {
        int median = _mapUnderBenchmark.size()/2 -1;
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        Stream<Map.Entry<String, Long>> vals = _mapUnderBenchmark
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() < median);
        long sizeOfStream = vals.count();
//        vals.forEach(val -> System.out.println(val.getKey() + "-->" + val.getValue()));  // temp dbg
        return "streamFilterValuesLessThanMedian took (size:" + sizeOfStream +") " + nsw.Stop() + "ns";
    }

    @Override
    public String streamSortAndReduceToSum() {
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();

        // NOTE: SUM will be different every time because of replaceRandomth !!!!
        long sum = _mapUnderBenchmark
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getValue)
                .reduce(0L, Long::sum);
        
        return "streamSortAndReduceToSum (result:" + sum + ") took " + nsw.Stop() + "ns";
    }  

    @Override 
    public String streamSortAndCopyToNewMap() {
        NanoStopwatch nsw = new NanoStopwatch();
        nsw.Start();
        try {
            Map<String,Long> mapSorted = _mapUnderBenchmark.getClass().newInstance();
            _mapUnderBenchmark
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue())
                    .forEachOrdered( kv -> mapSorted.put(kv.getKey(), kv.getValue() ) );
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(MapMeasuresBase.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return "streamSortAndCopyToNewMap took " + nsw.Stop() + "ns";
    }
    
    
}
