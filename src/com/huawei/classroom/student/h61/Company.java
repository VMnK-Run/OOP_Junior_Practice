package com.huawei.classroom.student.h61;

public class Company extends Place{
    public Company(int i) {
        super(i);
    }

    @Override
    public void addPerson(Person person) {
        super.addPerson(person);
        person.setCompany(this);
    }
}
