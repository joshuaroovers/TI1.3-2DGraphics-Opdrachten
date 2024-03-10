import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Random;

public class ScreensaverPoint {
    double posX;
    double posY;
    double directionX;
    double directionY;
    ScreensaverPoint endpoint;
    int maxHistory;
    Line2D[] linesHistory;
    int lineHistoryWriteStep;

    public ScreensaverPoint(double posX, double posY, ScreensaverPoint endpoint, int maxHistory) {
        this.posX = posX;
        this.posY = posY;
        this.endpoint = endpoint;
        linesHistory = new Line2D[maxHistory];
        this.maxHistory = maxHistory;
        lineHistoryWriteStep = 0;

        Random randomDirX = new Random();
        if(randomDirX.nextInt(2) == 0){
            directionX = 4;
        }else{directionX = -4;}

        Random randomDirY = new Random();
        if(randomDirY.nextInt(2) == 0) {
            directionY = 4;
        }else{directionY = -4;}
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setEndpoint(ScreensaverPoint endpoint) {
        this.endpoint = endpoint;
    }

    public void update(ResizableCanvas canvas){
        if(posY <= 0 || posY >= canvas.getHeight()){
            //top or bottom of screen
            directionY = -directionY;
        }
        if(posX <= 0 || posX >= canvas.getWidth()){
            //left-border or right-border of screen
            directionX = -directionX;
        }

        posX += directionX;
        posY += directionY;

        if(lineHistoryWriteStep == maxHistory){
            lineHistoryWriteStep = 0;
        }

    }

    public void draw(Graphics2D g2d){

        Line2D newLine = new Line2D.Double(posX,posY,endpoint.getPosX(),endpoint.getPosY());

        g2d.draw(newLine);

        for (Line2D line2D : linesHistory) {
            if(line2D != null)
                g2d.draw(line2D);
        }

        linesHistory[lineHistoryWriteStep] = newLine;
        lineHistoryWriteStep++;

    }
}
