package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import balloons.recursos.BalloonsImagens;
import balloons.recursos.BalloonsSons;
import balloons.util.BalloonsValores;


public class Balao extends BalloonsObjeto {
    private float tamanho;
    private float velocidade;
    private float subida;
    private float coordenadaX;
    private float coordenadaY;
    private Sprite balao;

    public Balao() {
        this.tamanho = 300*BalloonsValores.GAME_ESCALA;
        this.coordenadaY = (Gdx.graphics.getWidth()*50)/720;
        this.coordenadaX = Gdx.graphics.getWidth()/2 - tamanho/2;
        this.velocidade = 15* BalloonsValores.GAME_ESCALA;
        this.subida = BalloonsValores.GAME_ESCALA;
        this.balao = new Sprite(BalloonsImagens.imagem.balaoTexture);
        this.balao.setOriginCenter();
        this.balao.setSize(tamanho,tamanho);
        this.balao.setPosition(coordenadaX,coordenadaY);
    }

    private void moverDireita(){
        if( velocidade < 15) {
            BalloonsSons.som.audioBalao_atrito.stop();
            BalloonsSons.som.audioBalao_atrito.play();
        }
        if(coordenadaX + velocidade <= Gdx.graphics.getWidth() - tamanho/1.5){
            coordenadaX = coordenadaX + velocidade;
        }
        coordenadaY = coordenadaY + subida;
        balao.setPosition(coordenadaX, coordenadaY);
    }

    private void moverEsquerda(){
        if( velocidade < 15) {
            BalloonsSons.som.audioBalao_atrito.stop();
            BalloonsSons.som.audioBalao_atrito.play();
        }
        if(coordenadaX - velocidade >= -tamanho/3){
            coordenadaX = coordenadaX - velocidade;
        }
        coordenadaY = coordenadaY + subida;
        balao.setPosition(coordenadaX, coordenadaY);
    }

    private void moverCima() {
        coordenadaY = coordenadaY + subida;
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

    @Override
    public void renderizar(SpriteBatch batch) { balao.draw(batch); }

    @Override
    public boolean colisao(Balao balao) {
        return false;
    }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao) {
        if(Gdx.input.isTouched()){
            float pressX = Gdx.input.getX();
            float pressY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if(Math.sqrt(Math.pow(pressX,2)+Math.pow(pressY,2)) < Gdx.graphics.getWidth()/2){
                moverEsquerda();
            }else if(Math.sqrt(Math.pow(pressX - Gdx.graphics.getWidth(),2)+Math.pow(pressY,2)) < Gdx.graphics.getWidth()/2){
                moverDireita();
            }else
                moverCima();
        }else
            moverCima();
        renderizar(batch);
    }

    public void setVelocidade(float velocidade) {
        this.velocidade = velocidade;
    }

    public void setSubida(float subida) {
        this.subida = subida;
    }
}
