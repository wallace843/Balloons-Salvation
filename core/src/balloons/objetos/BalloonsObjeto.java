package balloons.objetos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BalloonsObjeto {
    public abstract Sprite getSprite();
    public abstract void renderizar(SpriteBatch batch);
    public abstract boolean colisao(Balao balao);
    public abstract void movimentar(Balao balao);
}
