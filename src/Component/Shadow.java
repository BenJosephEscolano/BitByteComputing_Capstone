package Component;

import GameEngine.Component;

public class Shadow extends Component {
    private Sprite shadow;

    public Shadow(Sprite shadow){
        this.shadow = shadow;
    }

    public Sprite getSubSprite(){
        return shadow;
    }

    @Override
    public Component copy() {
        return null;
    }
}
