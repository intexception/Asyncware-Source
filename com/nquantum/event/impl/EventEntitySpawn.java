package com.nquantum.event.impl;

import com.nquantum.event.Event;
import net.minecraft.entity.Entity;

public class EventEntitySpawn extends Event {
    private final Entity entity;

    public Entity getEntity() {
        return entity;
    }

    public EventEntitySpawn(Entity entity){
        this.entity = entity;
    }


}
