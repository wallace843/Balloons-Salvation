package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Aviao {
    private int tamanho;
    private float velocidade;
    private float coordenadaX;
    private float coordenadaY;
    private int alturaAviao;
    private boolean direitaAviao;
    private boolean naoSalvo;
    private float xAlvo;
    private Sprite sprite;
    private float a;
    private float b;
    private float ajusteRotacao;

    public Aviao(Texture texture, float posicaoX, float posicaoY){
        this.naoSalvo = true;
        this.tamanho = 250;
        this.sprite = new Sprite(texture);
        this.coordenadaX = posicaoX;;
        if(coordenadaX < Gdx.graphics.getWidth()/2)
            coordenadaX = - tamanho;
        this.coordenadaY = posicaoY;
        this.sprite.setSize(tamanho,tamanho);
        this.sprite.setOriginCenter();
        this.ajusteRotacao = -30;
        this.sprite.setPosition(coordenadaX,coordenadaY);
        this.alturaAviao = 350;
        this.direitaAviao = false;
        if(coordenadaX < Gdx.graphics.getWidth()/2){
            this.sprite.setFlip(true,false);
            this.sprite.setRotation(30);
            this.direitaAviao = true;
            ajusteRotacao = 30;
        }
        this.sprite.setRotation(ajusteRotacao);
    }

    public void movimentar(OrthographicCamera cam, float balaoX, float balaoY){
        if(cam.position.y - Gdx.graphics.getHeight()/2 <= coordenadaY +tamanho &&
                cam.position.y + Gdx.graphics.getHeight()/2 >= coordenadaY &&
                cam.position.y + Gdx.graphics.getHeight()/2 - coordenadaY > alturaAviao){
            if(naoSalvo){
                xAlvo = balaoX;
                naoSalvo = false;
                a = (float) ((- (coordenadaY - balaoY))/(Math.pow(coordenadaX - balaoX, 2)));
                b = (2*(coordenadaY - balaoY))/(coordenadaX - balaoX);
                this.velocidade = (xAlvo - coordenadaX)/80;
            }
            coordenadaY = (float) (a* Math.pow(coordenadaX - xAlvo,2) + b*(coordenadaX - xAlvo));

            sprite.setRotation((float) (Math.toDegrees(Math.atan(2*a*(coordenadaX - xAlvo) + b)) + ajusteRotacao));
            coordenadaX = coordenadaX + velocidade;
            coordenadaY = coordenadaY + balaoY;
        }
        if (velocidade > 0)
            sprite.setPosition(coordenadaX + Gdx.graphics.getWidth()/15, coordenadaY);
        else
            sprite.setPosition(coordenadaX, coordenadaY);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
