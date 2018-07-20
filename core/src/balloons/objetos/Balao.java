package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import balloons.Util.BalloonsConstants;


public class Balao extends BalloonsObjetos{
    private float tamanho;
    private float velocidade;
    private float coordenadaX;
    private float coordenadaY;
    private float vida;
    private Sprite balao;

    public Balao(Texture balaoTexture) {
        this.tamanho = 300;
        this.coordenadaY = 50;
        this.coordenadaX = Gdx.graphics.getWidth()/2 - tamanho/2;
        this.velocidade = 15;
        this.vida = BalloonsConstants.LARG_TELA/4f;
        this.balao = new Sprite(balaoTexture);
        this.balao.setOriginCenter();
        this.balao.setSize(tamanho,tamanho);
        this.balao.setPosition(coordenadaX,coordenadaY);
    }

    public void moverDireita(){
        if(coordenadaX + velocidade <= Gdx.graphics.getWidth() - tamanho/1.5){
            coordenadaX = coordenadaX + velocidade;
        }
        balao.setPosition(coordenadaX, ++coordenadaY);
    }

    public void moverEsquerda(){
        if(coordenadaX - velocidade >= -tamanho/3){
            coordenadaX = coordenadaX - velocidade;
        }
        balao.setPosition(coordenadaX, ++coordenadaY);
    }

    public void moverCima() {
        coordenadaY++;
        balao.setPosition(coordenadaX, coordenadaY);
    }

    public float getCoordenadaX() {
        return coordenadaX;
    }

    public float getCoordenadaY() {
        return coordenadaY;
    }

    public float getTamanho() {
        return tamanho;
    }

    public float getVida() {
        return vida;
    }

    public void setVida(float vida) {
        this.vida = vida;
    }

    public Sprite getSprite() {
        return balao;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }
}
