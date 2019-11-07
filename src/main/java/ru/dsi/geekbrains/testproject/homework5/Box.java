package ru.dsi.geekbrains.testproject.homework5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Box<T extends Fruit>{
    private List<T> fruits;
    private final static float COMPARSION_THRESHOLD = 0.0001f;

    public Box() {
        this.fruits = new ArrayList<>();
    }

    public void add(T fruit){
        this.fruits.add(fruit);
    }

    public void addAll(Collection<T> newFruits){
        this.fruits.addAll(newFruits);
    }

    public void pourAllTo(Box<T> box){
        if(box!=null){
            box.addAll(this.fruits);
        }//otherwise just throw out all fruits
        this.fruits.clear();
    }

    public float getWeight(){
        return this.fruits.stream().map(Fruit::getWeight).reduce(0f, Float::sum);
    }

    public boolean compare(Box<T> box) {
        return Math.abs(this.getWeight() - box.getWeight()) < COMPARSION_THRESHOLD;
    }
}
