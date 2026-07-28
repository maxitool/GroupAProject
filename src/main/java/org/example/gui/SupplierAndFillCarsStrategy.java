package org.example.gui;

import org.example.FillCarsStrategy;

import java.util.function.Supplier;

public class SupplierAndFillCarsStrategy {
    public final Supplier<Boolean> function;
    public final FillCarsStrategy fillCarsStrategy;

    public SupplierAndFillCarsStrategy(Supplier<Boolean> function, FillCarsStrategy fillCarsStrategy) {
        this.function = function;
        this.fillCarsStrategy = fillCarsStrategy;
    }
}
