package balloons.objetos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BalloonsObjetos {
    public abstract Sprite getSprite();

    public abstract void renderizar(SpriteBatch batch);
}
