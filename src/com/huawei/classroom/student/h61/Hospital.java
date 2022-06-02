package com.huawei.classroom.student.h61;

import java.util.*;

public class Hospital {
    private List<Person> waitQueue;
    private List<Person> treatQueue;
    private int hospitalSize;

    public Hospital(int size) {
        this.waitQueue = new LinkedList<>();
        this.treatQueue = new LinkedList<>();
        this.hospitalSize = size;
    }

    public void addPatient(Person person) {
        if(this.treatQueue.size() < this.hospitalSize) {
            treatQueue.add(person);
            person.setState(State.patientAtHospital);
        } else {
            waitQueue.add(person);
            person.setState(State.patientAtHome);
        }
    }

    public void removePatient(Person person) {
        treatQueue.remove(person);
        if(waitQueue.size() != 0) {
            Person waitedPerson = waitQueue.get(0);
            treatQueue.add(waitedPerson);
            waitQueue.remove(waitedPerson);
            waitedPerson.setState(State.patientAtHospital);
        }
    }
}
