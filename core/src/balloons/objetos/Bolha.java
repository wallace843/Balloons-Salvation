package balloons.objetos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

import balloons.recursos.BalloonsImagens;
import balloons.recursos.BalloonsSons;
import balloons.util.BalloonsValores;

public class Bolha extends BalloonsObjeto {
    private Sprite bolha[];
    private boolean colidiu[];
    private Circle cirBalao;
    private Circle cirBolha;

    public Bolha(float x, float y){
        bolha = new Sprite[4];
        colidiu = new boolean[4];
        if(x + 150*BalloonsValores.GAME_ESCALA > BalloonsValores.LARG_TELA)
            x = BalloonsValores.LARG_TELA - 150*BalloonsValores.GAME_ESCALA;
        int z = 0;
        for(int i = 0; i < 2 ; i++){
            for(int j = 0; j < 2; j++){
                bolha[z] = new Sprite(BalloonsImagens.imagem.bolhaTexure);
                bolha[z].setSize(25* BalloonsValores.GAME_ESCALA, 25* BalloonsValores.GAME_ESCALA);
                colidiu[z] = false;
                bolha[z].setPosition(x+50*i,y+70*j);
                z++;
            }
        }
        cirBalao = new Circle();
        cirBolha = new Circle();
    }

    @Override
    public void renderizar(SpriteBatch batch) {
        for (int i = 0; i < 4; i++){
            if(!colidiu[i])
                bolha[i].draw(batch);
        }
    }

    @Override
    public boolean colisao(Balao balao) {
        for (int i = 0; i < 4; i++){
            if(!colidiu[i]){
                cirBolha.setRadius(bolha[i].getWidth() * 0.48f);
                cirBalao.setRadius(balao.getTamanho()/5);
                cirBolha.setPosition((bolha[i].getWidth()/2) + bolha[i].getX(), bolha[i].getY() +( bolha[i].getHeight()/2));
                cirBalao.setPosition(balao.getTamanho()/2 + balao.getCoordenadaX(), balao.getTamanho()*3/5 + balao.getCoordenadaY());
                if(cirBalao.overlaps(cirBolha)){
                    atualizaVidaBalao();
                    colidiu[i] = true;
                }
            }
        }
        return false;
    }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao) {
        for(int i = 0; i < 4; i++){
            if(!colidiu[i]){
                if(bolha[i].getY() - balao.getCoordenadaY() < BalloonsValores.ALT_TELA &&
                        bolha[i].getY() + bolha[i].getHeight() > balao.getCoordenadaX()){
                    bolha[i].setPosition(bolha[i].getX(), bolha[i].getY() + BalloonsValores.GAME_ESCALA*0.4f);
                }
            }
        }
        colisao(balao);
        renderizar(batch);
    }

    private void atualizaVidaBalao(){
        int pt1 = (BalloonsValores.SCORE-BalloonsValores.SCORE%5000)/5000;
        BalloonsValores.SCORE += 300;
        int pt2 = (BalloonsValores.SCORE-BalloonsValores.SCORE%5000)/5000;
        BalloonsValores.VIDA =  BalloonsValores.VIDA +pt2-pt1;
        if(pt2 - pt1 == 0) {
            BalloonsSons.som.audioBolhas.stop();
            BalloonsSons.som.audioBolhas.play();
        }
        else
            BalloonsSons.som.audioVida.play();
    }
}
