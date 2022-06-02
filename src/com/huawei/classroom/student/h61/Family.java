package com.huawei.classroom.student.h61;

public class Family extends Place{

    public Family(int i) {
        super(i);
    }

    @Override
    public void addPerson(Person person) {
        super.addPerson(person);
        person.setFamily(this);
    }
}
