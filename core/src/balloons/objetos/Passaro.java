package balloons.objetos;

import com.badlogic.gdx.Gdx;
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

    public Passaro(Array<TextureRegion> passaroVoo, Array<TextureRegion> passaroPara, Array<TextureRegion> passaroAtaq, float posicaoX, float posicaoY){
        this.deltaTime = Gdx.graphics.getDeltaTime();
        this.passaroVoo = new Animation<TextureRegion>(1f/12f,passaroVoo, Animation.PlayMode.LOOP);
        this.passaroPara = new Animation<TextureRegion>(1f/12f,passaroPara,Animation.PlayMode.LOOP);
        this.passaroAtaq = new Animation<TextureRegion>(1f/12f,passaroAtaq);
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
        atualizarSprite((TextureRegion) this.passaroVoo.getKeyFrame(deltaTime,true));
    }

    public void movimentar(Balao balao, float deltaTime){
        float alturaBalao = balao.getSprite().getY() + balao.getSprite().getHeight() + 10;
        float xBalao = balao.getSprite().getX();
        if(y - alturaBalao < distanciaAtaque){
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
        if(BalloonsConstants.LARG_TELA/2 < x
                || BalloonsConstants.LARG_TELA/2 > x + 200){
            x = x + deslX;
            y = y - deslY;
            deltaTime += Gdx.graphics.getDeltaTime();
            atualizarSprite((TextureRegion) this.passaroVoo.getKeyFrame(deltaTime,true));
        }else{
            distanciaBase = y - alturaBalao + 10;
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
        if(distanciaBase > 9) {
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
        if(!passaroAtaq.isAnimationFinished(deltaTime)){
            atualizarSprite((TextureRegion) this.passaroAtaq.getKeyFrame(deltaTime));
            distanciaBase = distanciaBase + deslY;
            x = x + deslX;
            y = alturaBalao + distanciaBase;
            deltaTime += Gdx.graphics.getDeltaTime();
        }else {
            flagRetorno = true;
            flagAtaque = false;
            deltaTime = 0f;
        }
    }

    private void passaroRetorno(float alturaBalao, float xBalao){
        System.out.println(distanciaBase);
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
