package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.Random;
import balloons.Util.BalloonsConstants;

public class Passaro extends BalloonsObjetos{
    private Sprite sprite;
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
    float deltaTime;
    private Sound audioPassaro_asas;
    private Sound audioPassaro_canto;

    public Passaro(Array<TextureRegion> passaroVoo, Array<TextureRegion> passaroPara, Array<TextureRegion> passaroAtaq, float posicaoX, float posicaoY){
        this.deltaTime = Gdx.graphics.getDeltaTime();
        this.passaroVoo = new Animation<TextureRegion>(1f/24f,passaroVoo, Animation.PlayMode.LOOP);
        this.passaroPara = new Animation<TextureRegion>(1f/24f,passaroPara,Animation.PlayMode.LOOP);
        this.passaroAtaq = new Animation<TextureRegion>(1f/36f,passaroAtaq);
        this.y = posicaoY;
        this.x = posicaoX;
        this.velocidade = 10;
        if(posicaoX + 200 > Gdx.graphics.getWidth())
            this.x = Gdx.graphics.getWidth() - 200;
        this.flip = false;
        if(posicaoX + 200 <= BalloonsConstants.LARG_TELA/2) {
            deslX = 5;
            direita = true;
        }
        else if(posicaoX > BalloonsConstants.LARG_TELA/2) {
            deslX = -5;
            flip = true;
            direita = false;
        }
        deslY = (float) Math.sqrt(Math.pow(velocidade,2) - Math.pow(deslX,2));
        Random aleatorio = new Random();
        this.nAtaques = aleatorio.nextInt(2) + 3;
        this.distanciaAtaque = BalloonsConstants.ALT_TELA/1.5f;
        this.flagInicio = true;
        this.flagAproximacao = false;
        this.flagAtaque = false;
        this.flagRetorno = false;
        this.flagSaida = false;
        this.audioPassaro_asas = Gdx.audio.newSound(Gdx.files.internal("sons/passaro_asas.wav"));
        this.audioPassaro_canto = Gdx.audio.newSound(Gdx.files.internal("sons/passaro_canto.wav"));
        atualizarSprite((TextureRegion) this.passaroVoo.getKeyFrame(deltaTime,true));
    }

    public void movimentar(Balao balao){
        float alturaBalao = balao.getSprite().getY() + balao.getSprite().getHeight() - 50;
        float xBalao = balao.getSprite().getX();
        if(y - alturaBalao < distanciaAtaque && x + sprite.getWidth() > 0 && x < BalloonsConstants.LARG_TELA ){
            if(flagInicio){
                passaroInicial(alturaBalao,xBalao);
            }else if(flagAproximacao){
                passaroAproximacao(alturaBalao);
            }else if(flagAtaque){
                passaroAtaque(alturaBalao);
            }else if(flagRetorno){
                passaroRetorno(alturaBalao,balao.getSprite().getX());
            }else if(flagSaida){
                passaroSair();
            }
        }
    }

    private void passaroInicial(float alturaBalao, float xBalao){
        if(passaroVoo.getKeyFrameIndex(deltaTime) == 3)
            audioPassaro_asas.play();
        if(BalloonsConstants.LARG_TELA/2 < x
                || BalloonsConstants.LARG_TELA/2 > x + 200){
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

    private void passaroAproximacao(float alturaBalao){
        if(passaroPara.getKeyFrameIndex(deltaTime) == 3)
            audioPassaro_asas.play();
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
        if(passaroPara.getKeyFrameIndex(deltaTime) == 3)
            audioPassaro_asas.play();
        if(!passaroAtaq.isAnimationFinished(deltaTime)){
            distanciaBase = distanciaBase + deslY;
            x = x + deslX;
            y = alturaBalao + distanciaBase;
            deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.passaroAtaq.getKeyFrame(deltaTime));
        }else {
            audioPassaro_canto.play();
            flagRetorno = true;
            flagAtaque = false;
            deltaTime = 0f;
        }
    }

    private void passaroRetorno(float alturaBalao, float xBalao){
        if(passaroPara.getKeyFrameIndex(deltaTime) == 3)
            audioPassaro_asas.play();
        if(distanciaBase < BalloonsConstants.ALT_TELA/2) {
            distanciaBase = distanciaBase + velocidade;
            y = alturaBalao + distanciaBase;
            this.deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.passaroPara.getKeyFrame(deltaTime,true));
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

    private void passaroSair(){
        if(passaroVoo.getKeyFrameIndex(deltaTime) == 3)
            audioPassaro_asas.play();
        if(x + 100 >= BalloonsConstants.LARG_TELA/2){
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
        this.sprite = new Sprite(textureRegion);
        sprite.setSize(200,200);
        sprite.setPosition(x,y);
        sprite.setFlip(flip,false);
   }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void renderizar(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
