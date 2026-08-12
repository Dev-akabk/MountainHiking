package model.entity;

/**
 * Entity chứa thông tin một đỉnh núi.
 *
 * @author Legion
 */
public class Mountain {

    private String mountainCode;
    private String mountainName;
    private String province;
    private String description;

    public Mountain() {
    }

    public Mountain(String mountainCode, String mountainName, String province, String description) {
        this.mountainCode = mountainCode;
        this.mountainName = mountainName;
        this.province = province;
        this.description = description;
    }

    public String getMountainCode() {
        return mountainCode;
    }

    public void setMountainCode(String mountainCode) {
        this.mountainCode = mountainCode;
    }

    public String getMountainName() {
        return mountainName;
    }

    public void setMountainName(String mountainName) {
        this.mountainName = mountainName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%-10s| %-25s| %-15s| %-20s",
                mountainCode, mountainName, province, description);
    }
}
