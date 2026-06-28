package com.example.techcorp;

import com.example.techcorp.engine.GameEngine;
import com.example.techcorp.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        GameEngine engine = new GameEngine();
        ConsoleUI ui = new ConsoleUI(engine);
        ui.start();
    }
}