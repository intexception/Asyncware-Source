package com.nquantum.module.misc;

import cf.nquan.util.ChatUtil;
import com.nquantum.event.EventTarget;
import com.nquantum.event.impl.EventReceivePacket;
import com.nquantum.event.impl.EventSendPacket;
import com.nquantum.module.Category;
import com.nquantum.module.Module;

public class PacketLogger extends Module {
    public PacketLogger(){
        super("PacketLogger", 0, Category.MISC);
    }

    @EventTarget
    public void onSendPacket(EventSendPacket nigger) {
        ChatUtil.sendMessagePrefix("Packet sent! " + nigger.getPacket().toString()  + " | Time: " + System.currentTimeMillis());
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket nigger) {
        ChatUtil.sendMessagePrefix("Packet Received! " + nigger.getPacket().toString() + " | Time: " + System.currentTimeMillis());
    }
}
