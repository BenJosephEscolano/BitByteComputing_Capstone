package GameEngine;

import DataStructure.Transform;
import Util.Vector;
import Component.Collision;
import Component.BoxBounds;
import Component.*;

import java.awt.*;
import java.util.List;

public class Bullet extends GameObject {
    private int radius;
    private Vector velocity;  // store the bullet's velocity
    private List<GameObject> collision = Window.getWindow().getScene().getCollisionLayer();
    private GameObject player1, player2;
    private boolean live = true;

    public Bullet(Vector position, Vector velocity) {
        super("bullet", new Transform(position));
        this.radius = 10;
        this.velocity = velocity;
        this.player1 = Window.getWindow().getScene().getPlayer1();
        this.player2 = Window.getWindow().getScene().getPlayer2();
    }

    @Override
    public void update(double dt) {
        // Update the position based on velocity
        Vector position = getPosition();
        position.setX(position.getX() + velocity.getX() * (float) dt);
        position.setY(position.getY() + velocity.getY() * (float) dt);
        if (live){
            for (GameObject c: collision){
                if (Collision.checkCollision(c.getComponent(BoxBounds.class), getComponent(BoxBounds.class))){
                    if (c == player1 ){
                        Window.getWindow().getScene().getRenderer(1).unsubmit(player1);
                        Window.getWindow().getScene().playerDead(1);
                    }
                    if (c == player2){
                        Window.getWindow().getScene().getRenderer(1).unsubmit(player2);
                        Window.getWindow().getScene().playerDead(2);
                    }
                    Window.getWindow().getScene().getRenderer(2).unsubmit(this);
                    live = false;
                    System.out.println(collision.size());
                }
            }
        }



        // Print bullet position and velocity for debugging
        //System.out.println("Bullet Position: " + position.getX() + ", " + position.getY());
        //System.out.println("Bullet Velocity: " + velocity.getX() + ", " + velocity.getY());
    }

    /*@Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE); // Bullet will be red for visibility
        g2.fillOval((int) getX() + 10, (int) getY() + 10, radius * 2, radius * 2); // Draw the bullet as a circle
        System.out.println((int) getY());
    }*/
}
