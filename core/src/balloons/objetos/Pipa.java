package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import balloons.Util.BalloonsConstants;

public class Pipa extends BalloonsObjetos {
    private Sprite sprite;

    public Pipa(Texture texture, float posicaoX, float posicaoY){
        float tamanho = 270;
        this.sprite = new Sprite(texture);
        this.sprite.setSize(16*tamanho/9,tamanho);
        sprite.setOriginCenter();
        if(posicaoX + sprite.getWidth() > Gdx.graphics.getWidth()) {
            posicaoX = Gdx.graphics.getWidth() - sprite.getWidth();
            sprite.setFlip(true, false);
        }
        this.sprite.setPosition(posicaoX,posicaoY);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
