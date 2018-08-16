package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import balloons.recursos.BalloonsImagens;
import balloons.recursos.BalloonsSons;
import balloons.util.BalloonsValores;

public class Galho extends BalloonsObjeto {
    private Sprite galho;
    private boolean colidiu;
    private Rectangle retBalao;
    private Rectangle retGalho;
    private int cont;

    public Galho(float posicaoX, float posicaoY){
        this.retBalao = new Rectangle();
        this.retGalho = new Rectangle();
        this.colidiu = false;
        float tamanho = BalloonsValores.LARG_TELA*0.8f;
        this.galho = new Sprite(BalloonsImagens.imagem.galhoTexure);
        this.galho.setSize(tamanho,tamanho);
        this.galho.setOriginCenter();
        if(posicaoX + galho.getWidth()/2 < Gdx.graphics.getWidth()/2) {
            posicaoX = 0;
            galho.setFlip(true, false);
        }else {
            posicaoX = BalloonsValores.LARG_TELA - galho.getWidth();
        }
        this.galho.setPosition(posicaoX,posicaoY);
    }

    @Override
    public void renderizar(SpriteBatch batch) { galho.draw(batch); }

    @Override
    public boolean colisao(Balao balao) {
        retBalao.set(balao.getCoordenadaX() + 130f,balao.getCoordenadaY() + 230, 40, 20);
        if(galho.getX() == 0)
            retGalho.set(0, galho.getY() + galho.getWidth()*0.357f, galho.getWidth()*11.69f/15f, 20);
        else
            retGalho.set(galho.getX() + galho.getWidth() * (15 - 11.69f) / 15f, galho.getY() + galho.getWidth()*0.357f, galho.getWidth() * 11.69f / 15f, 20);
        if(retBalao.overlaps(retGalho)){
            if(cont == 60){
                BalloonsValores.VIDA--;
                if (BalloonsValores.VIDA < 0 )
                    BalloonsSons.som.audioBalaoEstouro.play();
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
