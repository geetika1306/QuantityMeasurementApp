package com.apps.quantitymeasurement;

import java.util.Objects;

public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid value or unit");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }

    // ---------- Conversion ----------
    public QuantityWeight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityWeight(converted, targetUnit);
    }

    // ---------- Equality ----------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double thisBase = this.unit.convertToBaseUnit(this.value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisBase - otherBase) < 1e-6;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(unit.convertToBaseUnit(value) * 100000));
    }

    // ---------- Addition (UC6 style) ----------
    public static QuantityWeight add(QuantityWeight w1, QuantityWeight w2) {
        return add(w1, w2, w1.unit);
    }

    public static QuantityWeight add(QuantityWeight w1, QuantityWeight w2, WeightUnit targetUnit) {
        if (w1 == null || w2 == null || targetUnit == null) {
            throw new IllegalArgumentException("Null not allowed");
        }

        double sumBase =
                w1.unit.convertToBaseUnit(w1.value) +
                        w2.unit.convertToBaseUnit(w2.value);

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityWeight(result, targetUnit);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}
