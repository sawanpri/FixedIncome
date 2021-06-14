package com.jeff.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.nio.file.Paths;
import java.util.List;

public class Publisher{
    Observable<List<Trade>> publisher=null;

    private List<Trade> readJSON(String path) {
        try{
            return new ObjectMapper().readValue(Paths.get(path).toAbsolutePath().toFile(), new TypeReference<List<Trade>>(){});
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Observable<List<Trade>> getPublisher(String[] paths){
        if(publisher!=null) return publisher;

        return publisher = Observable.fromArray(paths).map(path->{
            Thread.sleep(1000);
            return  readJSON(path);
        }).subscribeOn(Schedulers.computation());
    }
}
