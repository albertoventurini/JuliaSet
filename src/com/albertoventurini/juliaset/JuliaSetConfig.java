package com.albertoventurini.juliaset;

public class JuliaSetConfig {

    private int width;
    private int height;
    private int maxIterations;
    private double zoom;
    private double cx;
    private double cy;
    private double moveX;
    private double moveY;

    public JuliaSetConfig(
            final int width,
            final int height,
            final int maxIterations,
            final double zoom,
            final double cx,
            final double cy,
            final double moveX,
            final double moveY
    ) {

        this.width = width;
        this.height = height;
        this.maxIterations = maxIterations;
        this.zoom = zoom;
        this.cx = cx;
        this.cy = cy;
        this.moveX = moveX;
        this.moveY = moveY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public double getCx() {
        return cx;
    }

    public void setCx(double cx) {
        this.cx = cx;
    }

    public double getCy() {
        return cy;
    }

    public void setCy(double cy) {
        this.cy = cy;
    }

    public double getMoveX() {
        return moveX;
    }

    public void setMoveX(double moveX) {
        this.moveX = moveX;
    }

    public double getMoveY() {
        return moveY;
    }

    public void setMoveY(double moveY) {
        this.moveY = moveY;
    }
}
