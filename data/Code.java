package data;

class Code {
    static final int MAX_VALUE = 100;

    int heatResistance;
    int coldResistance;
    int lightSensitivity;
    int maxEnergy;

    public Code(int heatResistance, int coldResistance, int lightSensitivity,
        int maxEnergy) {
        this.heatResistance = heatResistance;
        this.coldResistance = coldResistance;
        this.lightSensitivity = lightSensitivity;
        this.maxEnergy = maxEnergy;
    }

    static Code generateRandomCode() {
        return new Code(
            (int) (Math.random() * Code.MAX_VALUE),
            (int) (Math.random() * Code.MAX_VALUE),
            (int) (Math.random() * Code.MAX_VALUE),
            (int) (Math.random() * Code.MAX_VALUE));
    }

    public int getHeatResistance() {
        return heatResistance;
    }

    public int getColdResistance() {
        return coldResistance;
    }

    public int getLightSensitivity() {
        return lightSensitivity;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }
}