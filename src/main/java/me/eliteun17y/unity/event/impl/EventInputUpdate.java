package me.eliteun17y.unity.event.impl;

import me.eliteun17y.unity.event.Event;
import net.minecraft.util.MovementInput;

public class EventInputUpdate extends Event {
    public MovementInput movementInput;

    public EventInputUpdate(MovementInput movementInput) {
        this.movementInput = movementInput;
    }

    public MovementInput getMovementInput() {
        return movementInput;
    }

    public void setMovementInput(MovementInput movementInput) {
        this.movementInput = movementInput;
    }
}
