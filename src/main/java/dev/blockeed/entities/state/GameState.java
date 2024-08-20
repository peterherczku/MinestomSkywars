package dev.blockeed.entities.state;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.timer.Task;
import net.minestom.server.timer.TaskSchedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class GameState {

    private EventNode<Event> events;
    @Getter
    private int time;
    private Task task;

    public GameState(int time) {
        this.time = time;
    }

    public void onEnable() {
        this.events = events();
        MinecraftServer.getGlobalEventHandler().addChild(events);
        task = MinecraftServer.getSchedulerManager().buildTask(this::run).repeat(TaskSchedule.tick(20)).schedule();
    }

    public void onDisable() {
        MinecraftServer.getGlobalEventHandler().removeChild(events);
        if (task.isAlive()) task.cancel();
    }

    public void run() {
        time--;
        if (time == 0) {
            task.cancel();
            onEnd();
        }
    }

    public abstract void onEnd();

    public abstract EventNode<Event> events();

}
