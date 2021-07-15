package com.nquantum.event.impl;

import com.nquantum.event.Event;

public class Event3D extends Event {
    public float partialTicks;

    public Event3D(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
