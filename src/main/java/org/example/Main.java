package org.example;

import org.example.gui.GUISingleton;

public class Main {
    public static void main(String[] args) {
        GUISingleton gui = GUISingleton.getInstance();
        gui.run();
    }
}
