package com.example.workoutsets.entity;

public class WorkoutSet {

    int id;
    String name;
    String date;
    String time;
    int weight;
    int reps;
    String stage;
    String max;

    public WorkoutSet(){}

    public WorkoutSet( String name, String date, String time,
                      int weight, int reps, String stage) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.weight = weight;
        this.reps = reps;
        this.stage = stage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String userDisplay(){
        String text = String.format("%3s |  %5s |  %3s | %8s | %8s",weight, reps, max, stage, date);
        return text;
    }

    @Override
    public String toString() {
        return "WorkoutSet{" +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", weight=" + weight +
                ", reps=" + reps +
                ", stage='" + stage + '\'' +
                '}';
    }
}
