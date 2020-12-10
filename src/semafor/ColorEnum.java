/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semafor;

/**
 *
 * @author Netbeans
 */
public enum ColorEnum {
    TGreenYellowRed(false,false,true),
    GreenTYellowRed(false,true,false),
    GreenYellowTRed(true,false,false),
    Nothing(false, false, false);

    private ColorEnum(boolean green, boolean yellow, boolean red) {
        this.green = green;
        this.yellow = yellow;
        this.red = red;
    }
    
    public boolean green;
    public boolean yellow;
    public boolean red;

    @Override
    public String toString() {
        return "ColorEnum{" + "green=" + green + ", yellow=" + yellow + ", red=" + red + '}';
    }
    
    
}
