package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Pipa extends BalloonsObjetos {
    private Sprite sprite;
    private Animation frame;
    private float deltaTime;

    public Pipa(Array<TextureRegion> pipaMove, float posicaoX, float posicaoY){
        this.deltaTime = 0f;
        this.frame = new Animation(1f/6f,pipaMove,Animation.PlayMode.LOOP_PINGPONG);
        float tamanho = 270;
        this.sprite = new Sprite((TextureRegion) frame.getKeyFrame(deltaTime,true));
        this.sprite.setSize(16*tamanho/9,tamanho);
        this.sprite.setOriginCenter();
        if(posicaoX + sprite.getWidth()/2 > Gdx.graphics.getWidth()/2) {
            posicaoX = Gdx.graphics.getWidth() - sprite.getWidth();
            sprite.setFlip(true, false);
        }
        this.sprite.setPosition(posicaoX,posicaoY);
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void renderizar(SpriteBatch batch) {

    }

    public void movimentar() {
        deltaTime += Gdx.graphics.getDeltaTime();
        Sprite tmp = new Sprite((TextureRegion) frame.getKeyFrame(deltaTime,true));
        tmp.setSize(sprite.getWidth(),sprite.getHeight());
        tmp.setPosition(sprite.getX(),sprite.getY());
        this.sprite.setOriginCenter();
        if(sprite.getX() + sprite.getWidth()/2 > Gdx.graphics.getWidth()/2) {
            tmp.setX(Gdx.graphics.getWidth() - sprite.getWidth());
            tmp.setFlip(true, false);
        }
        sprite = tmp;
    }
}
