package com.boss.cgpacalculator;

public class CGPAEntry {
    private double cgpa;
    private int credit;

    public CGPAEntry(double cgpa, int credit) {
        this.cgpa = cgpa;
        this.credit = credit;
    }

    public double getCgpa() {
        return cgpa;
    }

    public int getCredit() {
        return credit;
    }
}