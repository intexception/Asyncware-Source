package com.nquantum.misc.changelog;

import java.awt.*;

public enum ChangeType {
    NEW(new Color(53, 255, 109).getRGB()),
    UPDATE(new Color(64, 168, 168).getRGB()),
    FIX(new Color(255, 255, 69).getRGB()),
    DELETE(new Color(255, 9, 9, 2).getRGB());

    private final int color;

    ChangeType(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
