package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

import balloons.recursos.BalloonsImagens;

public class Nuvem extends BalloonsObjeto {
    private float velocidade;
    private float tamanho;
    private float coordenadaX;
    private float coordenadaY;
    private Sprite nuvem;

    public Nuvem(){
        Random aleatorio = new Random();
        this.velocidade = (float) (aleatorio.nextFloat() - 0.5)/5;
        this.tamanho = (float) (Math.sqrt(Math.pow(velocidade, 2))*1000000)%40 + 25;
        this.coordenadaX = aleatorio.nextInt(Gdx.graphics.getWidth() - 30);
        this.coordenadaY = aleatorio.nextInt(Gdx.graphics.getWidth()*10);
        this.nuvem = new Sprite(BalloonsImagens.imagem.nuvemTexture);
        nuvem.setSize(tamanho, tamanho*3/4);
        nuvem.setPosition(coordenadaX,coordenadaY);
    }

    @Override
    public void renderizar(SpriteBatch batch) {  nuvem.draw(batch); }

    @Override
    public boolean colisao(Balao balao) {
        return false;
    }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao) {
        if(balao.getCoordenadaY() <= coordenadaY + tamanho*3/4 &&
                balao.getCoordenadaY() + Gdx.graphics.getHeight() >= coordenadaY){
            coordenadaX = coordenadaX + velocidade;
        }
        nuvem.setPosition(coordenadaX, coordenadaY);
        renderizar(batch);
    }
}
