package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class Nuvem extends BalloonsObjetos{
    private float velocidade;
    private float tamanho;
    private float coordenadaX;
    private float coordenadaY;
    private Sprite nuvem;

    public Nuvem(Texture nuvemTexture){
        Random aleatorio = new Random();
        this.velocidade = (float) (aleatorio.nextFloat() - 0.5)/5;
        this.tamanho = (float) (Math.sqrt(Math.pow(velocidade, 2))*1000000)%40 + 25;
        this.coordenadaX = aleatorio.nextInt(Gdx.graphics.getWidth() - 30);
        this.coordenadaY = aleatorio.nextInt(Gdx.graphics.getWidth()*10);
        this.nuvem = new Sprite(nuvemTexture);
        nuvem.setSize(tamanho, tamanho*3/4);
        nuvem.setPosition(coordenadaX,coordenadaY);
    }

    public void movimentar(OrthographicCamera cam){
        if(cam.position.y - Gdx.graphics.getHeight()/2 <= coordenadaY + tamanho*3/4 &&
                cam.position.y + Gdx.graphics.getHeight()/2 >= coordenadaY){
            coordenadaX = coordenadaX + velocidade;
        }
        nuvem.setPosition(coordenadaX, coordenadaY);
    }

    public Sprite getSprite() {
        return nuvem;
    }

    @Override
    public void renderizar(SpriteBatch batch) {

    }
}
