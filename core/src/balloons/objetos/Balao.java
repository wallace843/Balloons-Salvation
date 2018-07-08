package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Balao {
    private float tamanho;
    private float velocidade;
    private float coordenadaX;
    private float coordenadaY;
    private Sprite balao;

    public Balao(Texture balaoTexture) {
        this.tamanho = 200;
        this.coordenadaY = 50;
        this.coordenadaX = Gdx.graphics.getWidth()/2 - tamanho/2;
        this.velocidade = 15;
        this.balao = new Sprite(balaoTexture);
        this.balao.setSize(tamanho,tamanho);
        this.balao.setPosition(coordenadaX,coordenadaY);
    }

    public void moverDireita(){
        if(coordenadaX + velocidade <= Gdx.graphics.getWidth() - tamanho){
            coordenadaX = coordenadaX + velocidade;
        }
        balao.setPosition(coordenadaX, ++coordenadaY);
    }

    public void moverEsquerda(){
        if(coordenadaX - velocidade >= 0){
            coordenadaX = coordenadaX - velocidade;
        }
        balao.setPosition(coordenadaX, ++coordenadaY);
    }

    public void moverCima() {
        coordenadaY++;
        balao.setPosition(coordenadaX, coordenadaY);
    }

    public Sprite getSprite() {
        return balao;
    }
}
