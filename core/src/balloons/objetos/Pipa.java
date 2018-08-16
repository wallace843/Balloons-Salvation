package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import balloons.recursos.BalloonsImagens;
import balloons.util.BalloonsValores;

public class Pipa extends BalloonsObjeto {
    private Sprite pipa;
    private Animation frame;
    private float deltaTime;
    private boolean colidiu;
    private Rectangle retBalao;
    private Rectangle retPipa;
    private int cont;

    public Pipa(float posicaoX, float posicaoY){
        this.cont = 0;
        this.retBalao = new Rectangle();
        this.retPipa = new Rectangle();
        this.colidiu = false;
        this.deltaTime = 0f;
        this.frame = new Animation(1f/6f,pegarImageFrames(),Animation.PlayMode.LOOP_PINGPONG);
        float tamanho = 270;
        this.pipa = new Sprite((TextureRegion) frame.getKeyFrame(deltaTime,true));
        this.pipa.setSize(16*tamanho/9,tamanho);
        this.pipa.setOriginCenter();
        if(posicaoX + pipa.getWidth()/2 > Gdx.graphics.getWidth()/2) {
            posicaoX = Gdx.graphics.getWidth() - pipa.getWidth();
            pipa.setFlip(true, false);
        }
        this.pipa.setPosition(posicaoX,posicaoY);
    }

    @Override
    public void renderizar(SpriteBatch batch) { pipa.draw(batch); }

    @Override
    public boolean colisao(Balao balao) {
        retBalao.set(balao.getCoordenadaX() + 130f,balao.getCoordenadaY() + 230, 40, 20);
        retPipa.set(pipa.getX(), pipa.getY() + 270f/4f, 16f*270f/9f, 20);
        if(retBalao.overlaps(retPipa)){
            if(cont == 60){
                BalloonsValores.VIDA--;
                cont = 0;
            }else
                cont++;
            balao.setVelocidade(5);
            balao.setSubida(0);
            return true;
        }else if(colidiu){
            balao.setVelocidade(15);
            balao.setSubida(1);
            return false;
        }
        return false;
    }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao) {
        if(pipa.getY() - balao.getCoordenadaY() < BalloonsValores.ALT_TELA){
            deltaTime += Gdx.graphics.getDeltaTime();
            Sprite tmp = new Sprite((TextureRegion) frame.getKeyFrame(deltaTime,true));
            tmp.setSize(pipa.getWidth(),pipa.getHeight());
            tmp.setPosition(pipa.getX(),pipa.getY());
            this.pipa.setOriginCenter();
            if(pipa.getX() + pipa.getWidth()/2 > Gdx.graphics.getWidth()/2) {
                tmp.setX(Gdx.graphics.getWidth() - pipa.getWidth());
                tmp.setFlip(true, false);
            }
            pipa = tmp;
        }
        colidiu = colisao(balao);
        renderizar(batch);
    }

    private Array<TextureRegion> pegarImageFrames(){
        Texture local = BalloonsImagens.imagem.pipaSheet;
        TextureRegion tmp[][] = TextureRegion.split(local, local.getWidth()/3, local.getHeight()/2);
        Array<TextureRegion> regions = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++) {
                regions.add(tmp[i][j]);
            }
        }
        return regions;
    }
}
