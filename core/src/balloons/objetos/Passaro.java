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

public class Passaro extends BalloonsObjeto {
    private boolean colidiu;
    private Sprite passaro;
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
    private Animation passaroVoo;
    private Animation passaroPara;
    private Animation passaroAtaq;
    private boolean flip;
    private float x;
    private float y;
    private float deltaTime;
    private Circle cirBalao;
    private Circle cirPassaro;

    public Passaro(float posicaoX, float posicaoY){
        this.cirBalao = new Circle();
        this.cirPassaro = new Circle();
        this.deltaTime = Gdx.graphics.getDeltaTime();
        this.passaroVoo = new Animation<TextureRegion>(1f/24f,pegarImageFrames(BalloonsImagens.imagem.passVooSheet), Animation.PlayMode.LOOP);
        this.passaroPara = new Animation<TextureRegion>(1f/24f,pegarImageFrames(BalloonsImagens.imagem.passParSheet),Animation.PlayMode.LOOP);
        this.passaroAtaq = new Animation<TextureRegion>(1f/36f,pegarImageFrames(BalloonsImagens.imagem.paassAtsheet));
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
        atualizarSprite((TextureRegion) this.passaroVoo.getKeyFrame(deltaTime,true));
    }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao){
        float alturaBalao = balao.getCoordenadaY() + balao.getTamanho() - 50;
        float xBalao = balao.getCoordenadaX();
        if(y - alturaBalao < distanciaAtaque && x + passaro.getWidth() > 0 && x < BalloonsValores.LARG_TELA ){
            if(flagInicio){
                passaroInicial(alturaBalao,xBalao);
            }else if(flagAproximacao){
                passaroAproximacao(alturaBalao);
            }else if(flagAtaque){
                passaroAtaque(alturaBalao);
            }else if(flagRetorno){
                passaroRetorno(alturaBalao,balao.getCoordenadaX());
            }else if(flagSaida){
                passaroSair();
            }
        }
        if(!colidiu)
            colisao(balao);
        renderizar(batch);
    }

    private void passaroInicial(float alturaBalao, float xBalao){
        if(passaroVoo.getKeyFrameIndex(deltaTime) == 3) {
            BalloonsSons.som.audioPassaro_asas.stop();
            BalloonsSons.som.audioPassaro_asas.play();
        }
        if(BalloonsValores.LARG_TELA/2 < x
                || BalloonsValores.LARG_TELA/2 > x + 200){
            x = x + deslX;
            y = y - deslY;
            deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.passaroVoo.getKeyFrame(deltaTime));
        }else{
            distanciaBase = y - alturaBalao + 15;
            float fator = (float) Math.sqrt(Math.pow(velocidade,2)/(Math.pow(distanciaBase,2)
                    + Math.pow(xBalao + 50 - x,2)));
            deslX = (xBalao + 50 - x)*fator;
            deslY = -distanciaBase * fator;
            if(deslX >= 0 && !direita){
                flip = false;
                direita = true;
            }else if(deslX < 0 && direita){
                flip = true;
                direita = false;
            }
            flagInicio = false;
            flagAproximacao = true;
            deltaTime = 0f;
        }
    }

    private void passaroAproximacao(float alturaBalao){
        colidiu = false;
        if(passaroVoo.getKeyFrameIndex(deltaTime) == 3) {
            BalloonsSons.som.audioPassaro_asas.stop();
            BalloonsSons.som.audioPassaro_asas.play();
        }
        if(distanciaBase > 60) {
            distanciaBase = distanciaBase + deslY;
            x = x +deslX;
            y = alturaBalao + distanciaBase;
            deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.passaroPara.getKeyFrame(deltaTime,true));
        }else {
            --nAtaques;
            flagAtaque = true;
            flagAproximacao = false;
            deltaTime = 0f;
        }
    }

    private void passaroAtaque(float alturaBalao){
        if(passaroVoo.getKeyFrameIndex(deltaTime) == 3) {
            BalloonsSons.som.audioPassaro_asas.stop();
            BalloonsSons.som.audioPassaro_asas.play();
        }
        if(!passaroAtaq.isAnimationFinished(deltaTime)){
            distanciaBase = distanciaBase + deslY;
            x = x + deslX;
            y = alturaBalao + distanciaBase;
            deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.passaroAtaq.getKeyFrame(deltaTime));
        }else {
            flagRetorno = true;
            flagAtaque = false;
            deltaTime = 0f;
        }
    }

    private void passaroRetorno(float alturaBalao, float xBalao){
        if(passaroVoo.getKeyFrameIndex(deltaTime) == 3) {
            BalloonsSons.som.audioPassaro_asas.stop();
            BalloonsSons.som.audioPassaro_asas.play();
        }
        if(distanciaBase < BalloonsValores.ALT_TELA/2) {
            distanciaBase = distanciaBase + velocidade;
            y = alturaBalao + distanciaBase;
            this.deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.passaroPara.getKeyFrame(deltaTime,true));
        }else if(nAtaques > 0){
            float fator = (float) Math.sqrt(Math.pow(velocidade,2)/(Math.pow(distanciaBase,2)
                    + Math.pow(xBalao + 50 - x,2)));
            deslX = (xBalao + 50 - x)*fator;
            deslY = -distanciaBase * fator;
            if(deslX >= 0 && !direita){
                flip = false;
                direita = true;
            }else if(deslX < 0 && direita){
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

    private void passaroSair(){
        if(passaroVoo.getKeyFrameIndex(deltaTime) == 3) {
            BalloonsSons.som.audioPassaro_asas.stop();
            BalloonsSons.som.audioPassaro_asas.play();
        }
        if(x + 100 >= BalloonsValores.LARG_TELA/2){
            x = x + velocidade;
            flip = false;
        }
        else{
            x = x - velocidade;
            flip = true;
        }
        this.deltaTime += Gdx.graphics.getDeltaTime();
        atualizarSprite((TextureRegion) this.passaroVoo.getKeyFrame(deltaTime,true));
    }

   private void atualizarSprite(TextureRegion textureRegion){
        this.passaro = new Sprite(textureRegion);
        passaro.setSize(200,200);
        passaro.setPosition(x,y);
        passaro.setFlip(flip,false);
   }

    @Override
    public void renderizar(SpriteBatch batch) { passaro.draw(batch); }

    @Override
    public boolean colisao(Balao balao) {
        cirBalao.setRadius(balao.getTamanho()/5);
        cirBalao.setPosition(balao.getTamanho()/2 + balao.getCoordenadaX(), balao.getTamanho()*3/5 + balao.getCoordenadaY());
        cirPassaro.setRadius(15);
        cirPassaro.setPosition(passaro.getWidth()/2 + passaro.getX(),20 + passaro.getY());
        if(cirBalao.overlaps(cirPassaro)) {
            if (BalloonsValores.VIDA == 0) {
                BalloonsSons.som.audioBalaoEstouro.stop();
                BalloonsSons.som.audioBalaoEstouro.play();
            }else {
                BalloonsSons.som.audioBalaoColisao.stop();
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
