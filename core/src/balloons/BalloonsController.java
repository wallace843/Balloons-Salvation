package balloons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Random;
import balloons.objetos.Balao;
import balloons.objetos.Nuvem;

public class BalloonsController {
    private Nuvem[] nuvens;
    private Balao balao;

    public BalloonsController(){
        Texture nuvemTexture = new Texture("nuvem.png");
        Texture balaoTexture = new Texture("balao.png");
        Random r = new Random();
        this.nuvens = new Nuvem[r.nextInt(50) + 450];
        this.balao = new Balao(balaoTexture);
        for(int i = 0; i < nuvens.length; i++){
            nuvens[i] = new Nuvem(nuvemTexture);
        }
    }

    public void atualizar(OrthographicCamera camera){
        atualizarBalao();
        atualizarNuvens(camera);
    }

    private void atualizarNuvens(OrthographicCamera cam){
        for(Nuvem n : nuvens){
            n.movimentar(cam);
        }
    }

    private void atualizarBalao(){
        if(Gdx.input.isTouched()){
            float pressX = Gdx.input.getX();
            float pressY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if(Math.sqrt(Math.pow(pressX,2)+Math.pow(pressY,2)) < Gdx.graphics.getWidth()/2){
                balao.moverEsquerda();
            }else if(Math.sqrt(Math.pow(pressX - Gdx.graphics.getWidth(),2)+Math.pow(pressY,2)) < Gdx.graphics.getWidth()/2){
                balao.moverDireita();
            }else
                balao.moverCima();
        }else
            balao.moverCima();
    }

    public Sprite[] gerarSprites(){
        Sprite[] sprites = new Sprite[nuvens.length + 1];
        int i = 0;
        for(Nuvem n: nuvens){
            sprites[i] = n.getSprite();
            i++;
        }
        sprites[i] = balao.getSprite();
        return sprites;
    }
}
