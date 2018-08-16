package balloons.objetos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class BalloonsObjeto {
    public abstract void renderizar(SpriteBatch batch);
    public abstract boolean colisao(Balao balao);
    public abstract void movimentar(SpriteBatch batch, Balao balao);
}
