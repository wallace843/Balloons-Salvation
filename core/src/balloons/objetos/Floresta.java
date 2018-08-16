package balloons.objetos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import balloons.util.BalloonsValores;


public class Floresta extends BalloonsObjeto {
    private Sprite floresta;
    public Floresta(Texture texture, float i){
        floresta = new Sprite(texture);
        floresta.setSize(BalloonsValores.LARG_TELA, BalloonsValores.LARG_TELA*0.4f);
        floresta.setPosition(0, BalloonsValores.LARG_TELA*0.4f*i);
    }

    @Override
    public void renderizar(SpriteBatch batch) { floresta.draw(batch); }

    @Override
    public boolean colisao(Balao balao){ return false; }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao) { renderizar(batch); }
}
