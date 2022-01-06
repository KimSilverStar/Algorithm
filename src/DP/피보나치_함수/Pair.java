package DP.피보나치_함수;

class Pair {
    private int numOfZero;
    private int numOfOne;

    public Pair(int numOfZero, int numOfOne) {
        this.numOfZero = numOfZero;
        this.numOfOne = numOfOne;
    }
    public int getNumOfZero() {
        return numOfZero;
    }
    public int getNumOfOne() {
        return numOfOne;
    }

    public String toString() {
        return numOfZero + " " + numOfOne;
    }
}
