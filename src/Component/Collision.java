package Component;


import GameEngine.GameObject;
import GameEngine.PlayerCharacter;
import Util.Constants;

//joseph
public class Collision {
    /*
    this check if there's a collision on four sides
    -if a player is top on platform it reset the jump
     */

    public static void checkPlatformPlayerCollision(Platform platform, GameObject player) {
        if (player == null) return;

        BoxBounds playerBounds = player.getComponent(BoxBounds.class);
        RigidBody rb = player.getComponent(RigidBody.class);
        if (playerBounds == null || rb == null) return;

        float playerBottom = player.getY() + playerBounds.getHeight();
        float playerTop = player.getY();
        float playerRight = player.getX() + playerBounds.getWidth();
        float playerLeft = player.getX();

        float platformTop = platform.getGameObject().getY();
        float platformBottom = platformTop + platform.getHeight();
        float platformLeft = platform.getGameObject().getX();
        float platformRight = platformLeft + platform.getWidth();

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
    
    public static boolean checkCollision(BoxBounds box1, BoxBounds box2){
        float box1Top = box1.getY();
        float box1Bottom = box1Top + box1.getHeight();
        float box1Left = box1.getX();
        float box1Right = box1Left + box1.getWidth();

        float box2Top = box2.getGameObject().getY();
        float box2Bottom = box2Top + box2.getHeight();
        float box2Left = box2.getGameObject().getX();
        float box2Right = box2Left + box2.getWidth();

        if (box1Right > box2Left && box1Left < box2Right &&
                box1Bottom > box2Top && box1Top < box2Bottom) {
            return true;
        }
        return false;
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

    public static boolean isOutOfBounds(PlayerCharacter box){
        if (box.getY() > Constants.SCREEN_HEIGHT && box.getAliveStatus()){
            box.setY(50);
        }
        if (box.getX()  < 0){
            box.setX(Constants.SCREEN_WIDTH);
        }
        if (box.getX() > Constants.SCREEN_WIDTH){
            box.setX(0);
        }
        return false;
    }

    public static void isOutOfBounds(GameObject box){
        if (box.getY() > Constants.SCREEN_HEIGHT){
            box.setY(50);
        }
        if (box.getX()  < 0){
            box.setX(Constants.SCREEN_WIDTH);
        }
        if (box.getX() > Constants.SCREEN_WIDTH){
            box.setX(0);
        }
    }

}
