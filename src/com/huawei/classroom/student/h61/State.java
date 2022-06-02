package com.huawei.classroom.student.h61;

public enum State {
    // 一个人只有以下几种状态
    // 其余的属性，如是否打疫苗，是否免疫在 Person 类中定义
    healthy, latent, patientAtHome, patientAtHospital, cured, dead
}
