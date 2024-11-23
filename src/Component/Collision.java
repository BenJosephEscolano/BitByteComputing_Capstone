package Component;

import GameEngine.GameObject;

//joseph
public class Collision {
    /*
    this check if there's a collision on four sides
    -if a player is top on platform it reset the jump
     */
    private static final float COLLISION_TOLERANCE = 5.0f;

    public static void checkPlatformPlayerCollision(Platform platform1, GameObject player) {
        if (player == null) return;

        BoxBounds playerBounds = player.getComponent(BoxBounds.class);
        RigidBody rb = player.getComponent(RigidBody.class);
        if (playerBounds == null || rb == null) return;

        float playerBottom = player.getY() + playerBounds.getHeight();
        float playerTop = player.getY();
        float playerRight = player.getX() + playerBounds.getWidth();
        float playerLeft = player.getX();

        float platformTop = platform1.getGameObject().getY();
        float platformBottom = platform1.getGameObject().getY() + platform1.getHeight();
        float platformLeft = platform1.getGameObject().getX();
        float platformRight = platform1.getGameObject().getX() + platform1.getWidth();

        if (playerRight > platformLeft && playerLeft < platformRight &&
                playerBottom > platformTop && playerTop < platformBottom) {

            float overlapLeft = playerRight - platformLeft;
            float overlapRight = platformRight - playerLeft;
            float overlapTop = playerBottom - platformTop;
            float overlapBottom = platformBottom - playerTop;

            float minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                    Math.min(overlapTop, overlapBottom));

            if (minOverlap == overlapTop && rb.velocity.getY() > 0) {

                player.setY(platformTop - playerBounds.getHeight());
                rb.velocity.setY(0);
                resetJumpState(player);
            } else if (minOverlap == overlapBottom && rb.velocity.getY() < 0) {

                player.setY(platformBottom);
                rb.velocity.setY(0);
            } else if (minOverlap == overlapLeft && rb.velocity.getX() > 0) {

                player.setX(platformLeft - playerBounds.getWidth());
                rb.velocity.setX(0);
            } else if (minOverlap == overlapRight && rb.velocity.getX() < 0) {

                player.setX(platformRight);
                rb.velocity.setX(0);
            }
        }
    }

    private static void resetJumpState(GameObject player) {
        PlayerOneControls playerOneControls = player.getComponent(PlayerOneControls.class);
        if (playerOneControls != null) {
            playerOneControls.hasJumped = false;
        }

        PlayerTwoControls playerTwoControls = player.getComponent(PlayerTwoControls.class);
        if (playerTwoControls != null) {
            playerTwoControls.hasJumped = false;
        }
    }
}
