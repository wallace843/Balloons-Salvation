package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import balloons.recursos.BalloonsImagens;
import balloons.util.BalloonsValores;

public class Poste extends BalloonsObjeto{
    private Sprite poste;
    private boolean colidiu;
    private Rectangle retBalao;
    private Rectangle retPoste;
    private int cont;

    public Poste(Texture posteTexture, float posicaoX, float posicaoY){
        this.retBalao = new Rectangle();
        this.retPoste = new Rectangle();
        this.colidiu = false;
        float tamanho = BalloonsValores.LARG_TELA*0.8f;
        this.poste = new Sprite(BalloonsImagens.imagem.posteTexure);
        this.poste.setSize(tamanho,tamanho);
        this.poste.setOriginCenter();
        if(posicaoX + poste.getWidth()/2 < Gdx.graphics.getWidth()/2) {
            posicaoX = 0;
            poste.setFlip(true, false);
        }else {
            posicaoX = BalloonsValores.LARG_TELA - poste.getWidth();
        }
        this.poste.setPosition(posicaoX,posicaoY);
    }

    @Override
    public void renderizar(SpriteBatch batch) { poste.draw(batch); }

    @Override
    public boolean colisao(Balao balao) {
        retBalao.set(balao.getCoordenadaX() + 130f,balao.getCoordenadaY() + 230, 40, 20);
        if(poste.getX() == 0)
            retPoste.set(poste.getX(), poste.getY() + poste.getWidth()*409/1500, poste.getWidth()*11.69f/15f, 20);
        else
            retPoste.set(poste.getX()+ poste.getWidth()*(15 - 11.69f)/15f, poste.getY() + poste.getWidth()*409/1500, poste.getWidth()*11.69f/15f, 20);
        if(retBalao.overlaps(retPoste)){
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
        colidiu = colisao(balao);
        renderizar(batch);
    }
}
