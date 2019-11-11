package ru.dsi.geekbrains.testproject.homework6;

import ru.dsi.geekbrains.testproject.exceptions.MyException;

import java.util.*;

public class PhoneDictionary {
    private Map<String, Set<String>> dictionary;
    private static final String NUMBER_REGEXP = "^\\d*$";

    public PhoneDictionary() {
        this.dictionary = new HashMap<>();
    }

    public void add(String name, String number) throws MyException {
        if(name==null){
            throw new MyException("name is null");
        }
        if(number==null){
            throw new MyException("number is null");
        }
        if(!number.matches(NUMBER_REGEXP)){
            throw new MyException("Wrong number format: number = '"+number+"', format = '"+NUMBER_REGEXP+"'");
        }
        this.dictionary.putIfAbsent(name, new HashSet<>());
        this.dictionary.get(name).add(number);
    }

    public Set<String> get(String name){
        return this.dictionary.getOrDefault(name, Collections.emptySet());
    }
}
