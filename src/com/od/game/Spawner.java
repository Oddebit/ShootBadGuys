package com.od.game;

import com.od.objects.Enemy;
import com.od.objects.GameObject;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class Spawner extends GameObject {

    Random random = new Random();

    ObjectHandler oHandler;
    SurroundingsHandler sHandler;
    private Instant lastTime;
    private long timeInGame;

    public Spawner(ObjectHandler oHandler, SurroundingsHandler sHandler) {
        super(-1000, -1000, 0, 0, ID.Spawner);
        this.lastTime = Instant.now();
        this.oHandler = oHandler;
        this.sHandler = sHandler;
    }

    @Override
    public void tick() {
        if (lastTime.plusSeconds(1).isBefore(Instant.now())){
            lastTime = lastTime.plusSeconds(1);
            timeInGame++;

            if(timeInGame < 60) {
                if(random.nextInt(100) < 33) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame < 120) {
                if((random.nextInt(100) < 50)) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame < 180) {
                if((random.nextInt(100) < 66)) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            }
        }
    }

    @Override
    public void render(Graphics graphics) {}

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,(int)w,(int)h);
    }
}