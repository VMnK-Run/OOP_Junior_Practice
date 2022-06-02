package com.huawei.classroom.student.h61;

public class Person {
    private Param param;
    private int id; // 编号
    private State state; // 状态
    private int latentDays; // 潜伏期
    private Family family; // 所属家庭
    private Company company; // 所属公司

    private boolean isImmune; // 是否获得免疫
    private boolean isVaccine; //是否注射疫苗

    public Person(int id, Param param) {
        this.id = id;
        this.state = State.healthy;
        this.latentDays = 0;
        this.isVaccine = false;
        this.param = param;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public void setVaccine(boolean value) {
        this.isVaccine = value;
    }

    public boolean hasVirus() {
        return this.state == State.latent || this.state == State.patientAtHome || this.state == State.patientAtHospital;
    }

    public boolean isDead() {
        return this.state == State.dead;
    }

    public boolean isCured() {
        return this.state == State.cured;
    }

    /**
     * 试一下能不能被感染
     * */
    public void tryInfected(double infectRate) {
        if(this.isImmune || this.hasVirus()) {
            return;
        }
        if(this.isVaccine) {
            if(Util.getProbability() < infectRate * (1 - param.getImmuEffect())) {
                this.setState(State.latent);
            }
        } else {
            if(Util.getProbability() < infectRate) {
                this.setState(State.latent);
            }
        }
    }

    /**
     * 试一下能不能痊愈
     * */
    public void tryHeal(double healingRate) {
        if(this.isDead()) { // 都死了还痊愈个锤子
            return;
        }
        if(Util.getProbability() < healingRate) {
            this.setState(State.cured);
            this.isImmune = true;
        }
    }

    /**
     * 试着去死
     * */
    public void tryDeath(double deathRate) {
        if(Util.getProbability() < deathRate) {
            this.setState(State.dead);
        }
    }

    public void oneDayPass(Hospital hospital) {
        if(this.state == State.healthy) {
            if(!this.isImmune) {
                if(this.company.hasVirusToday()) { // 白天在公司
                    this.tryInfected(param.getSpreadRateCompany());
                }
                if(this.family.hasVirusToday()) { // 晚上在家
                    this.tryInfected(param.getSpreadRateFamily());
                }
            }
        } else if (this.state == State.latent) {
            this.latentDays++;
            if(this.latentDays > param.getLatentPeriod()) {
                hospital.addPatient(this);
            }
        } else if (this.state == State.patientAtHome) {
            this.tryDeath(param.getDeathRateHome());
            this.tryHeal(param.getHealingRateHome());
        } else if (this.state == State.patientAtHospital) {
            this.tryDeath(param.getDeathRateHospital());
            this.tryHeal(param.getHealingRateHospital());
            if(this.isCured() || this.isDead()) { // 死了或者治好了都要从医院里滚蛋
                hospital.removePatient(this);
            }
        }
    }
}
