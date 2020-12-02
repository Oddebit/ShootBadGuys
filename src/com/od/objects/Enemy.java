package com.od.objects;

import com.od.game.Game;
import com.od.game.ObjectHandler;
import com.od.game.ID;
import com.od.game.SurroundingsHandler;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject {

    Random random = new Random();

    ObjectHandler oHandler;
    SurroundingsHandler sHandler;
    Hero hero;

    private static final float diameter = 40;
    private final float speed = 3;

    private int HP = 20;
    private boolean isHittingHero = false;

    public Enemy(float x, float y, ObjectHandler oHandler, SurroundingsHandler sHandler) {
        super(x, y, diameter, diameter, ID.Enemy);
        this.oHandler = oHandler;
        this.sHandler = sHandler;

        setStartingPosition();

        for (int i = 0; i < oHandler.objects.size(); i++) {
            GameObject tempObject = oHandler.objects.get(i);
            if (tempObject.getId() == ID.Hero) hero = (Hero) tempObject;
        }
    }

    @Override
    public void tick() {

        float deltaX = x + diameter / 2 - hero.getX() - hero.getW() / 2;
        float deltaY = y + diameter / 2 - hero.getY() - hero.getH() / 2;
        float distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        velocityX = -speed * deltaX / distance;
        velocityY = -speed * deltaY / distance;

        x += velocityX;
        y += velocityY;

        collision();

        if (HP <= 0) oHandler.removeObject(this);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(0, Math.max(6 * HP, 0), Math.max(6 * HP, 0)));
        graphics.fillOval((int) x, (int) y, (int) diameter, (int) diameter);
    }

    public void collision() {
        boolean hitsHero = false;
        for (int i = 0; i < oHandler.objects.size(); i++) {
            GameObject tempObject = oHandler.objects.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                if (tempObject.getId() == ID.Hero) {
                    if (!isHittingHero) {
                        hero.setHP(hero.getHP() - HP);
                    }
                    hitsHero = true;
                }
                if (tempObject.getId() == ID.Projectile) {
                    if (((Projectile) tempObject).getShooter() == hero) {
                        oHandler.removeObject(tempObject);
                        sHandler.addSurrounding(new Blood(x, y, sHandler));
                        this.HP -= ((Projectile) tempObject).getDamage();
                    }
                }
            }
        }
        isHittingHero = hitsHero;
    }

    public void setStartingPosition() {
        int proba = random.nextInt(4);
        switch (proba) {
            case 0:
                this.y = 0;
                break;
            case 1:
                this.x = 0;
                break;
            case 2:
                this.y = Game.REAL_HEIGHT - (int) Enemy.getDiameter();
                break;
            case 3:
                this.x = Game.REAL_WIDTH - (int) Enemy.getDiameter();
                break;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) diameter, (int) diameter);
    }

    public static float getDiameter() {
        return diameter;
    }

    public float getSpeed() {
        return speed;
    }

    public int getHP() {
        return HP;
    }
}
