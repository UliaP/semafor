package semafor;

import graphicsModel.GraphicsModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import static semafor.ColorEnum.GreenTYellowRed;
import static semafor.ColorEnum.GreenYellowTRed;
import static semafor.ColorEnum.TGreenYellowRed;
import static semafor.ColorEnum.Nothing;

public class StateSemaphor implements Runnable {

    ChangeColor green;
    ChangeColor red;
    ChangeColor yellow;
    ChangeColor nothing;
    ChangeColor state;
    ChangeColor oldState;
    GraphicsModel gm;
    ColorEnum colorEnum;
    boolean suspendFlag = false;
    int time;

    public StateSemaphor(GraphicsModel model) {
        green = new Green();
        red = new Red();
        yellow = new Yellow();
        nothing = new Nothing();
        state = red;
        oldState = red;
        time = 10;
        gm = model;
        colorEnum = ColorEnum.GreenYellowTRed;
        suspendFlag = false;
    }

    public ColorEnum print() {
        return colorEnum;
    }

    public void changeState() {
        if (colorEnum.equals(colorEnum.TGreenYellowRed) || colorEnum.equals(colorEnum.Nothing)) {
            blink();
        } else {
            state.changeColor();
            gm.setColor(colorEnum);
        }
    }
    
    public void blink() {
        if (colorEnum.equals(colorEnum.Nothing)) {
            state = green;
            colorEnum = ColorEnum.TGreenYellowRed;
        } else {
            state = nothing;
            colorEnum = ColorEnum.Nothing;
        }
        gm.setColor(colorEnum);
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            changeState();
            stop();
        }

    }

    private void stop() {
        try {
            Thread.sleep(500);
            synchronized (this) {
                while (suspendFlag) {
                    wait();
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(StateSemaphor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void mysuspend() {
        suspendFlag = true;
    }

    public synchronized void myresume() {
        suspendFlag = false;
        notify();
    }

    public interface ChangeColor {
        void changeColor();
    }
    
    public class Nothing implements ChangeColor {

        @Override
        public void changeColor() {
            oldState = nothing;
            state = red;
            colorEnum = GreenYellowTRed;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Green.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class Green implements ChangeColor {

        @Override
        public void changeColor() {
            oldState = green;
            state = yellow;
            colorEnum = GreenTYellowRed;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Green.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class Yellow implements ChangeColor {

        @Override
        public void changeColor() {
            if (oldState == red) {
                oldState = yellow;
                state = green;
                colorEnum = TGreenYellowRed;
            } else {
                oldState = yellow;
                state = red;
                colorEnum = GreenYellowTRed;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Yellow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public class Red implements ChangeColor {

        @Override
        public void changeColor() {
            oldState = red;
            state = yellow;
            colorEnum = GreenTYellowRed;
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Red.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
