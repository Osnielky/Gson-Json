package com.example.groomer;

import android.text.Editable;

class Pet {

    public String name, weight, instruccions, breed;


    public Pet(String name, String weight, String instruccions, String breed) {
        this.name = name;
        this.weight = weight;
        this.instruccions = instruccions;
        this.breed = breed;
    }


    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getInstruccions() {
        return instruccions;
    }

    public String getBreed() {
        return breed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setInstruccions(String instruccions) {
        this.instruccions = instruccions;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

}
