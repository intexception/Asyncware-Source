package com.nquantum.event.impl;

import com.nquantum.event.Event;

public class EventHeadRender extends Event{

    public float headYaw;
    public float headPitch;



    public EventHeadRender(float headYaw, float headPitch)
    {
            this.headYaw = headYaw;
            this.headPitch = headPitch;

    }



}
