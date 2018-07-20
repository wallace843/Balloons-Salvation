package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.Random;

import balloons.Util.BalloonsConstants;

public class Passaro extends BalloonsObjetos{
    private Sprite sprite;
    private int nAtaques;
    private float velocidade;
    private float distanciaAtaque;
    private boolean flagInicio;
    private boolean flagAtaque;
    private boolean flagRetorno;
    private boolean flagSaida;
    private float deslX;
    private float deslY;
    private float distanciaBase;
    private boolean direita;

    public Passaro(Texture texture, float posicaoX, float posicaoY){
        this.sprite = new Sprite(texture);
        this.sprite.setSize(200,199);
        if(posicaoX + sprite.getWidth() > Gdx.graphics.getWidth())
            posicaoX = Gdx.graphics.getWidth() - sprite.getWidth() - 1;
        this.sprite.setPosition(posicaoX,posicaoY);
        this.sprite.setOriginCenter();
        Random aleatorio = new Random();
        this.nAtaques = aleatorio.nextInt(2) + 3;
        this.velocidade = 10;//5 + 1000/ (BalloonsConstants.LARG_TELA*10);
        this.distanciaAtaque = BalloonsConstants.ALT_TELA/1.5f;
        this.flagInicio = true;
        this.flagAtaque = false;
        this.flagRetorno = false;
        this.flagSaida = false;
        if(posicaoX + sprite.getWidth() <= BalloonsConstants.LARG_TELA/2) {
            deslX = 5;
            this.sprite.setRotation(-45);
            this.sprite.rotate(0);
            direita = true;
        }
        else if(posicaoX > BalloonsConstants.LARG_TELA/2) {
            deslX = -5;
            this.sprite.setRotation(45);
            sprite.setFlip(true,false);
            direita = false;
        }
        deslY = (float) Math.sqrt(Math.pow(velocidade,2) - Math.pow(deslX,2));
    }

    public void movimentar(Balao balao){
       // velocidade = 5 + 20*balao.getSprite().getY()/ (BalloonsConstants.LARG_TELA*10);
        float alturaBalao = balao.getSprite().getY() + balao.getSprite().getHeight() - 80;
        if(sprite.getY() - alturaBalao < distanciaAtaque){
            if(flagInicio){
                if(BalloonsConstants.LARG_TELA/2 < sprite.getX()
                        || BalloonsConstants.LARG_TELA/2 > sprite.getX() + sprite.getWidth()){
                    sprite.setPosition(sprite.getX()+deslX, sprite.getY() - deslY);
                }else{
                    distanciaBase = sprite.getY() - alturaBalao;
                    float fator = (float) Math.sqrt(Math.pow(velocidade,2)/(Math.pow(distanciaBase,2)
                            + Math.pow(balao.getSprite().getX() + 50 - sprite.getX(),2)));
                    deslX = (balao.getSprite().getX() + 50 - sprite.getX())*fator;
                    deslY = -distanciaBase * fator;
                    if(deslX >= 0 && direita == false){
                        sprite.setRotation(-45);
                        sprite.setFlip(false,false);
                        direita = true;
                    }else if(deslX < 0 && direita == true){
                        sprite.setRotation(45);
                        sprite.setFlip(true,false);
                        direita = false;
                    }else{
                        if(deslX >= 0)
                            sprite.setRotation(-45);
                        else
                            sprite.setRotation(45);
                    }
                    flagInicio = false;
                    flagAtaque = true;
                }
            }else if(flagAtaque){
                if(distanciaBase > 0) {
                    distanciaBase = distanciaBase + deslY;
                    if(-distanciaBase / deslY < 9){
                        if(direita)
                            sprite.rotate(-10f);
                        else
                            sprite.rotate(10f);
                    }
                    sprite.setPosition(sprite.getX() + deslX,alturaBalao + distanciaBase);
                }else {
                    --nAtaques;
                    flagAtaque = false;
                    flagRetorno = true;
                }
            }else if(flagRetorno){
                if(distanciaBase < BalloonsConstants.ALT_TELA/2) {
                    distanciaBase = distanciaBase + velocidade;
                    if(distanciaBase / velocidade < 9){
                        if(direita)
                            sprite.rotate(10f);
                        else
                            sprite.rotate(-10f);
                    }
                    sprite.setPosition(sprite.getX(), alturaBalao + distanciaBase);
                }
                else if(nAtaques > 0){
                    float fator = (float) Math.sqrt(Math.pow(velocidade,2)/(Math.pow(distanciaBase,2)
                            + Math.pow(balao.getSprite().getX() + 50 - sprite.getX(),2)));
                    deslX = (balao.getSprite().getX() + 50 - sprite.getX())*fator;
                    deslY = -distanciaBase * fator;
                    if(deslX >= 0 && direita == false){
                        sprite.setRotation(-45);
                        sprite.setFlip(false,false);
                        direita = true;
                    }else if(deslX < 0 && direita == true){
                        sprite.setRotation(45);
                        sprite.setFlip(true,false);
                        direita = false;
                    }else{
                        if(deslX >= 0)
                            sprite.setRotation(-45);
                        else
                            sprite.setRotation(45);
                    }
                    flagAtaque = true;
                    flagRetorno =  false;
                }
                else{
                    flagSaida = true;
                    flagRetorno = false;
                }
            }else if(flagSaida){
                if(sprite.getX() + sprite.getWidth()/2 >= BalloonsConstants.LARG_TELA/2){
                    sprite.setPosition(sprite.getX() + velocidade, sprite.getY());
                    sprite.setRotation(-45);
                    sprite.setFlip(false,false);
                }
                else{
                    sprite.setPosition(sprite.getX() - velocidade, sprite.getY());
                    sprite.setRotation(45);
                    sprite.setFlip(true,false);
                }
            }
        }
    }

    public Sprite getSprite() {
        return sprite;
    }
}
