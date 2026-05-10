package com.example.trainticketing.model;

public class JourneyOption {
    private Train firstTrain;
    private Train secondTrain;
    private String changeoverStation;

    public JourneyOption(Train firstTrain, Train secondTrain, String changeoverStation) {
        this.firstTrain = firstTrain;
        this.secondTrain = secondTrain;
        this.changeoverStation = changeoverStation;
    }

    public Train getFirstTrain() {
        return firstTrain;
    }

    public void setFirstTrain(Train firstTrain) {
        this.firstTrain = firstTrain;
    }

    public Train getSecondTrain() {
        return secondTrain;
    }

    public void setSecondTrain(Train secondTrain) {
        this.secondTrain = secondTrain;
    }

    public String getChangeoverStation() {
        return changeoverStation;
    }

    public void setChangeoverStation(String changeoverStation) {
        this.changeoverStation = changeoverStation;
    }

    public boolean isDirectJourney() {
        return secondTrain == null;
    }

    @Override
    public String toString() {
        if (isDirectJourney()) {
            return "Direct journey: " + firstTrain;
        }

        return "Changeover journey:\n"
                + "First train: " + firstTrain.getTrainId() + " - " + firstTrain.getTrainName() + "\n"
                + "Changeover station: " + changeoverStation + "\n"
                + "Second train: " + secondTrain.getTrainId() + " - " + secondTrain.getTrainName();
    }
}