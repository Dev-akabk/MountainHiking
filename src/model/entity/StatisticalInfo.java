package model.entity;

/**
 *
 * @author Legion
 */
public class StatisticalInfo {

    private String mountainCode;
    private String mountainName;
    private int numOfStudent;
    private double totalCost;

    public StatisticalInfo() {
    }

    public StatisticalInfo(String mountainName, String mountainCode, int numOfStudent, double totalCost) {
        this.mountainName = mountainName;
        this.mountainCode = mountainCode;
        this.numOfStudent = numOfStudent;
        this.totalCost = totalCost;
    }

    public String getMountainCode() {
        return mountainCode;
    }

    public void setMountainCode(String mountainCode) {
        this.mountainCode = mountainCode;
    }

    public int getNumOfStudent() {
        return numOfStudent;
    }

    public void setNumOfStudent(int numOfStudent) {
        this.numOfStudent = numOfStudent;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getMountainName() {
        return mountainName;
    }

    public void setMountainName(String mountainName) {
        this.mountainName = mountainName;
    }

    @Override
    public String toString() {
        return String.format("%-20s|%-10s| %-24d| %-15.2f",
                mountainName, getMountainCode(), numOfStudent, totalCost);
    }
}
