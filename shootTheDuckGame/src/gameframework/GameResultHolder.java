package gameframework;

import javafx.beans.property.SimpleIntegerProperty;

//klasa trzyma wyniki gry
//po prostu aktualizuje wyniki
// pola sa typu SimpleIntegerProperty zeby mozna bylo je powiazac z labelkami tak, aby java fx automatycznie aktualizowala ekran
public class GameResultHolder {
    private SimpleIntegerProperty shootsCount;
    private SimpleIntegerProperty killedCount;
    private SimpleIntegerProperty runawayCount;
    private SimpleIntegerProperty scoreCount;

    public GameResultHolder() {
        this.shootsCount = new SimpleIntegerProperty(this, "shootsCount", 0);
        this.killedCount = new SimpleIntegerProperty(this, "killedCount", 0);
        this.runawayCount = new SimpleIntegerProperty(this, "runawayCount", 0);
        this.scoreCount = new SimpleIntegerProperty(this, "scoreCount", 0);
    }

    public void duckKilled(Duck duck) {
        setKilledCount(getKilledCount() + 1);
        //punkty za kaczke to jej predkosc
        setScoreCount(getScoreCount() + duck.getSpeed());
    }

    public void resetValues() {
        setKilledCount(0);
        setRunawayCount(0);
        setShootsCount(0);
        setScoreCount(0);
    }

    public void shootMissed() {
        setShootsCount(getShootsCount() + 1);
    }

    public void duckMissed() {
        setRunawayCount(getRunawayCount() + 1);
    }

    public int getShootsCount() {
        return shootsCount.get();
    }

    public SimpleIntegerProperty shootsCountProperty() {
        return shootsCount;
    }

    public void setShootsCount(int shootsCount) {
        this.shootsCount.set(shootsCount);
    }

    public int getKilledCount() {
        return killedCount.get();
    }

    public SimpleIntegerProperty killedCountProperty() {
        return killedCount;
    }

    public void setKilledCount(int killedCount) {
        this.killedCount.set(killedCount);
    }

    public int getRunawayCount() {
        return runawayCount.get();
    }

    public SimpleIntegerProperty runawayCountProperty() {
        return runawayCount;
    }

    public void setRunawayCount(int runawayCount) {
        this.runawayCount.set(runawayCount);
    }

    public int getScoreCount() {
        return scoreCount.get();
    }

    public SimpleIntegerProperty scoreCountProperty() {
        return scoreCount;
    }

    public void setScoreCount(int scoreCount) {
        this.scoreCount.set(scoreCount);
    }
}
