package com.huawei.classroom.student.h61;

import java.util.ArrayList;
import java.util.List;

public abstract class Place {
    private int id;
    private List<Person> personList;
    private boolean hasVirus;

    public Place(int id) {
        this.id = id;
        this.hasVirus = false;
        this.personList = new ArrayList<Person>();
    }

    public int getId() {
        return this.id;
    }

    public boolean hasVirusToday() {
        return this.hasVirus;
    }

    public void setHasVirus(boolean value) {
        this.hasVirus = value;
    }

    public void addPerson(Person person) {
        personList.add(person);
    }

    public List<Person> getPersonList(){
        return this.personList;
    }
}
