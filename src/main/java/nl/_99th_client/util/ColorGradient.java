package nl._99th_client.util;

import java.awt.Color;

public class ColorGradient
{
    public int lowerBound;
    public Color lowerColor;

    public int upperBound;
    public Color upperColor;

    public ColorGradient(int lowerBound, int upperBound, Color lowerColor, Color upperColor) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        this.lowerColor = lowerColor;
        this.upperColor = upperColor;
    }

    public Color getColor(int pointer) {
        return this.getColor((float) pointer);
    }

    public Color getColor(float pointer) {
        if(pointer <= this.lowerBound) return this.lowerColor;
        if(pointer >= this.upperBound) return this.upperColor;

        float f = (pointer - lowerBound) / (upperBound - lowerBound);

        float r = lowerColor.getRed() + f * (upperColor.getRed() - lowerColor.getRed());
        float g = lowerColor.getGreen() + f * (upperColor.getGreen() - lowerColor.getGreen());
        float b = lowerColor.getBlue() + f * (upperColor.getBlue() - lowerColor.getBlue());

        return new Color((int) r, (int) g, (int) b);
    }
}
