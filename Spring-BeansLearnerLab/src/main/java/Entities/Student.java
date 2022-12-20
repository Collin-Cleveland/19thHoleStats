package Entities;

import Interfaces.Learner;

public class Student extends Person implements Learner {

    public double totalStudyTime;
    public Student(Long id, String name) {super(id, name);}

    @Override
    public void learn(double numOfHours) {
        totalStudyTime += numOfHours;
    }

    public double getTotalStudyTime(){
        return totalStudyTime;
    }
}
