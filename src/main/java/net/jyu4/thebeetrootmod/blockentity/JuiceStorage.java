package net.jyu4.thebeetrootmod.blockentity;

import net.minecraftforge.energy.EnergyStorage;

public class JuiceStorage extends EnergyStorage {
    public JuiceStorage(int capacity) {
        super(capacity);
    }

    public JuiceStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public JuiceStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public JuiceStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }


    public void setEnergy(int energy) {
        if(energy < 0)
            energy = 0;
        if(energy > this.capacity)
            energy = this.capacity;

        this.energy = energy;
    }

    public void addEnergy(int energy) {
        setEnergy(this.energy + energy);
    }

    public void removeEnergy(int energy) {
        setEnergy(this.energy - energy);
    }
}
