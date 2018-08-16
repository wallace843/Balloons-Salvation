package balloons.objetos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import balloons.util.BalloonsValores;

public class Predio extends BalloonsObjeto {
    Sprite predio;
    public Predio(Texture predioTexture, float i){
        this.predio = new Sprite(predioTexture);
        predio.setSize(BalloonsValores.LARG_TELA, BalloonsValores.LARG_TELA*0.4f);
        predio.setPosition(0, BalloonsValores.LARG_TELA*0.4f*i);
    }

    @Override
    public void renderizar(SpriteBatch batch){ predio.draw(batch); }

    @Override
    public boolean colisao(Balao balao){ return false; }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao){ renderizar(batch); }
}
