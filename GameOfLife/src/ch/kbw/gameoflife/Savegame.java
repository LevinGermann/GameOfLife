/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.kbw.gameoflife;

import java.io.Serializable;

/**
 *
 * @author 5im12legermann
 */
public class Savegame implements Serializable{
    private Cell[][] cells;

    public Savegame(Cell[][] cells) {
        this.cells = cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
