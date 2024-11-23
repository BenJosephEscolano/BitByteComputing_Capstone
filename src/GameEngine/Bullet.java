package GameEngine;

import DataStructure.Transform;
import Util.Vector;

import java.awt.*;

public class Bullet extends GameObject {
    private int radius;
    private Vector velocity;  // store the bullet's velocity

    public Bullet(Vector position, Vector velocity) {
        super("bullet", new Transform(position));
        this.radius = 10;
        this.velocity = velocity;
    }

    @Override
    public void update(double dt) {
        // Update the position based on velocity
        Vector position = getPosition();
        position.setX(position.getX() + velocity.getX() * (float) dt);
        position.setY(position.getY() + velocity.getY() * (float) dt);

        // Print bullet position and velocity for debugging
        System.out.println("Bullet Position: " + position.getX() + ", " + position.getY());
        System.out.println("Bullet Velocity: " + velocity.getX() + ", " + velocity.getY());
    }


    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLUE); // Bullet will be red for visibility
        g2.fillOval((int) getX(), (int) getY(), radius * 2, radius * 2); // Draw the bullet as a circle
    }
}
