package com.huawei.classroom.student.h61;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulate {

    private Param param;
    private List<Person> personList = new ArrayList<Person>();
    private List<Family> familyList = new ArrayList<Family>();
    private List<Company> companyList = new ArrayList<Company>();
    private Hospital hospital;

    public SimResult sim(Param param, int days) {
        this.param = param;
        this.hospital = new Hospital(param.getHospitalSize());
        init();
        for (int i = 0; i < days; i++) {
            aDayPass();
        }
        SimResult simResult = getSimResult(); // 等到最后再统计所有的人数情况
        return simResult;
    }

    public void aDayPass() {
        for(Family family : familyList) {
            resetVirusOfPlace(family);
        }
        for(Company company : companyList) {
            resetVirusOfPlace(company);
        }
        for(Person person : personList) {
            person.oneDayPass(this.hospital);
        }
    }

    /**
     * 初始化
     */
    public void init() {
        initPerson();
        initFamily();
        initCompany();
        initPatient();
    }

    /**
     *  初始化所有人口
     */
    public void initPerson() {
        for(int i = 0; i < param.getCityPopulation(); i++) {
            Person person = new Person(i, this.param);
            if(Util.getProbability() < param.getImmuRate()) {
                person.setVaccine(true);
            }
            personList.add(person);
        }
    }

    public void initFamily() {
        int familyCount = param.getCityPopulation() / param.getFamilySize();
        for (int i = 0; i < familyCount; i++) {
            Family family = new Family(i);
            for(int j = 0; j < param.getFamilySize(); j++) {
                family.addPerson(personList.get(j + i * param.getFamilySize()));
            }
            familyList.add(family);
        }
    }

    public void initCompany() {
        int companyCount = param.getCityPopulation() / param.getCompanySize();

        List<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < param.getCityPopulation(); i++) {
            list.add(i);
        }
        Collections.shuffle(list); // 打乱编号
        for (int i = 0; i < companyCount; i++) {
            Company company = new Company(i);
            for(int j = 0; j < param.getCompanySize(); j++) {
                Person e = personList.get(list.get(j + i * param.getCompanySize()));
                company.addPerson(e);
            }
            companyList.add(company);
        }
    }

    public void initPatient() {
        List<Integer> initPatients = param.getInitPatients();
        for(int i = 0; i < initPatients.size(); i++) {
            Person patient = personList.get(initPatients.get(i));
            patient.setState(State.latent);
        }
    }

    public SimResult getSimResult() {
        SimResult result = new SimResult();
        int deaths = 0;
        int cured = 0;
        int patients = 0;
        int latents = 0;
        for(Person person : personList) {
            if(person.getState() == State.dead) {
                deaths++;
            } else if (person.getState() == State.cured) {
                cured++;
            } else if (person.getState() == State.patientAtHome || person.getState() == State.patientAtHospital) {
                patients++;
            } else if (person.getState() == State.latent) {
                latents++;
            }
        }
        result.setDeaths(deaths);
        result.setCured(cured);
        result.setPatients(patients);
        result.setLatents(latents);
        return result;
    }

    public void resetVirusOfPlace(Place place) {
        for(Person person : place.getPersonList()) {
            if(person.hasVirus()) {
                place.setHasVirus(true);
                return;
            }
        }
        place.setHasVirus(false);
    }

}
