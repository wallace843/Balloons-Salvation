package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

import balloons.recursos.BalloonsImagens;
import balloons.recursos.BalloonsSons;
import balloons.util.BalloonsValores;

public class Drone extends BalloonsObjeto {
    private boolean colidiu;
    private Sprite drone;
    private int nAtaques;
    private float velocidade;
    private float distanciaAtaque;
    private boolean flagInicio;
    private boolean flagAproximacao;
    private boolean flagAtaque;
    private boolean flagRetorno;
    private boolean flagSaida;
    private float deslX;
    private float deslY;
    private float distanciaBase;
    private boolean direita;
    private Animation droneVoo;
    private Animation dronePara;
    private Animation droneAtaq;
    private boolean flip;
    private float x;
    private float y;
    float deltaTime;
    private Circle cirBalao;
    private Circle cirDrone;
    private int cont;

    public Drone(float posicaoX, float posicaoY){
        this.cont = 0;
        this.cirBalao = new Circle();
        this.cirDrone = new Circle();
        this.deltaTime = Gdx.graphics.getDeltaTime();
        this.droneVoo = new Animation<TextureRegion>(1f/24f, pegarImageFrames(BalloonsImagens.imagem.droneSheet), Animation.PlayMode.LOOP);
        this.dronePara = new Animation<TextureRegion>(1f/24f, pegarImageFrames(BalloonsImagens.imagem.droneSheet),Animation.PlayMode.LOOP);
        this.droneAtaq = new Animation<TextureRegion>(1f/36f, pegarImageFrames(BalloonsImagens.imagem.droneSheet));
        this.y = posicaoY;
        this.x = posicaoX;
        this.velocidade = 10;
        if(posicaoX + 200 > Gdx.graphics.getWidth())
            this.x = Gdx.graphics.getWidth() - 200;
        this.flip = false;
        if(posicaoX + 200 <= BalloonsValores.LARG_TELA/2) {
            deslX = 5;
            direita = true;
        }
        else if(posicaoX > BalloonsValores.LARG_TELA/2) {
            deslX = -5;
            flip = true;
            direita = false;
        }
        deslY = (float) Math.sqrt(Math.pow(velocidade,2) - Math.pow(deslX,2));
        Random aleatorio = new Random();
        this.nAtaques = aleatorio.nextInt(2) + 3;
        this.distanciaAtaque = BalloonsValores.ALT_TELA/1.5f;
        this.flagInicio = true;
        this.flagAproximacao = false;
        this.flagAtaque = false;
        this.flagRetorno = false;
        this.flagSaida = false;
        atualizarSprite((TextureRegion) this.droneVoo.getKeyFrame(deltaTime,true));
    }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao){
        float alturaBalao = balao.getCoordenadaY() + balao.getTamanho() - 50;
        float xBalao = balao.getCoordenadaX();
        if(y - alturaBalao < distanciaAtaque && x + drone.getWidth() > 0 && x < BalloonsValores.LARG_TELA ){
            if(cont == 0){
                cont = 10;
                BalloonsSons.som.audioMotor.play(0.1f);
            }else
                cont--;
            if(flagInicio){
                droneInicial(alturaBalao,xBalao);
            }else if(flagAproximacao){
                droneAproximacao(alturaBalao);
            }else if(flagAtaque){
                droneAtaque(alturaBalao);
            }else if(flagRetorno){
                droneRetorno(alturaBalao,balao.getCoordenadaX());
            }else if(flagSaida){
                droneSair();
            }
        }
        if(!colidiu)
            colisao(balao);
        renderizar(batch);
    }

    private void droneInicial(float alturaBalao, float xBalao){
        if(BalloonsValores.LARG_TELA/2 < x
                || BalloonsValores.LARG_TELA/2 > x + 200){
            x = x + deslX;
            y = y - deslY;
            deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.droneVoo.getKeyFrame(deltaTime));
        }else{
            distanciaBase = y - alturaBalao + 15;
            float fator = (float) Math.sqrt(Math.pow(velocidade,2)/(Math.pow(distanciaBase,2)
                    + Math.pow(xBalao + 50 - x,2)));
            deslX = (xBalao + 50 - x)*fator;
            deslY = -distanciaBase * fator;
            if(deslX >= 0 && direita == false){
                flip = false;
                direita = true;
            }else if(deslX < 0 && direita == true){
                flip = true;
                direita = false;
            }
            flagInicio = false;
            flagAproximacao = true;
            deltaTime = 0f;
        }
    }

    private void droneAproximacao(float alturaBalao){
        colidiu = false;
        if(distanciaBase > 60) {
            distanciaBase = distanciaBase + deslY;
            x = x +deslX;
            y = alturaBalao + distanciaBase;
            deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.dronePara.getKeyFrame(deltaTime,true));
        }else {
            --nAtaques;
            flagAtaque = true;
            flagAproximacao = false;
            deltaTime = 0f;
        }
    }

    private void droneAtaque(float alturaBalao){
        if(!droneAtaq.isAnimationFinished(deltaTime)){
            distanciaBase = distanciaBase + deslY;
            x = x + deslX;
            y = alturaBalao + distanciaBase;
            deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.droneAtaq.getKeyFrame(deltaTime));
        }else {
            flagRetorno = true;
            flagAtaque = false;
            deltaTime = 0f;
        }
    }

    private void droneRetorno(float alturaBalao, float xBalao){
        if(distanciaBase < BalloonsValores.ALT_TELA/2) {
            distanciaBase = distanciaBase + velocidade;
            y = alturaBalao + distanciaBase;
            this.deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.dronePara.getKeyFrame(deltaTime,true));
        }else if(nAtaques > 0){
            float fator = (float) Math.sqrt(Math.pow(velocidade,2)/(Math.pow(distanciaBase,2)
                    + Math.pow(xBalao + 50 - x,2)));
            deslX = (xBalao + 50 - x)*fator;
            deslY = -distanciaBase * fator;
            if(deslX >= 0 && direita == false){
                flip = false;
                direita = true;
            }else if(deslX < 0 && direita == true){
                flip = true;
                direita = false;
            }
            flagAproximacao = true;
            flagRetorno =  false;
        }else{
            flagSaida = true;
            flagRetorno = false;
            this.deltaTime = 0f;
        }
    }

    private void droneSair(){
        if(x + 100 >= BalloonsValores.LARG_TELA/2){
            x = x + velocidade;
            flip = false;
        }
        else{
            x = x - velocidade;
            flip = true;
        }
        this.deltaTime += Gdx.graphics.getDeltaTime();
        atualizarSprite((TextureRegion) this.droneVoo.getKeyFrame(deltaTime,true));
    }

    private void atualizarSprite(TextureRegion textureRegion){
        this.drone = new Sprite(textureRegion);
        drone.setSize(200,200);
        drone.setPosition(x,y);
        drone.setFlip(flip,false);
    }

    @Override
    public void renderizar(SpriteBatch batch) { drone.draw(batch); }

    @Override
    public boolean colisao(Balao balao) {
        cirBalao.setRadius(balao.getTamanho()/5);
        cirBalao.setPosition(balao.getTamanho()/2 + balao.getCoordenadaX(), balao.getTamanho()*3/5 + balao.getCoordenadaY());
        cirDrone.setRadius(15);
        cirDrone.setPosition(drone.getWidth()/2 + drone.getX(),20 + drone.getY());
        if(cirBalao.overlaps(cirDrone)) {
            if (BalloonsValores.VIDA == 0) {
                BalloonsSons.som.audioBalaoEstouro.play();
            }else {
                BalloonsSons.som.audioBalaoColisao.play();
            }
            colidiu = true;
            BalloonsValores.VIDA--;
        }
        return false;
    }

    private Array<TextureRegion> pegarImageFrames(Texture local){
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
