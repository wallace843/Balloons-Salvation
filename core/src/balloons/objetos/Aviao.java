package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Aviao extends BalloonsObjetos {
    private int tamanho;
    private float velocidade;
    private float coordenadaX;
    private float coordenadaY;
    private int alturaAviao;
    private boolean direitaAviao;
    private boolean naoSalvo;
    private float xAlvo;
    private float yAlvo;
    private Sprite sprite;
    private float a;
    private float b;
    private float ajusteRotacao;
    private boolean colidiu;

    public Aviao(Texture texture, float posicaoX, float posicaoY){
        this.colidiu = false;
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
        this.alturaAviao = Gdx.graphics.getHeight()*4/10;
        this.direitaAviao = false;
        if(coordenadaX < Gdx.graphics.getWidth()/2){
            this.sprite.setFlip(true,false);
            this.sprite.setRotation(30);
            this.direitaAviao = true;
            ajusteRotacao = 30;
        }
        this.sprite.setRotation(ajusteRotacao);
    }

    public void movimentar(OrthographicCamera cam, Balao balao){
        if(cam.position.y - Gdx.graphics.getHeight()/2 <= coordenadaY +tamanho &&
                cam.position.y + Gdx.graphics.getHeight()/2 >= coordenadaY &&
                cam.position.y + Gdx.graphics.getHeight()/2 - coordenadaY > alturaAviao){
            if(colidiu){
                if(naoSalvo){
                    xAlvo = coordenadaX;
                    naoSalvo = false;
                    a = -20000000/Gdx.graphics.getWidth();
                    if (velocidade <= 0){
                        b = -2;
                        velocidade = -15;
                    }
                    else{
                        b = 2;
                        velocidade = 15;
                    }
                }
                coordenadaY = (float) ((a* Math.pow(coordenadaX - xAlvo, 2))/10000000 + b*(coordenadaX - xAlvo));
                if(velocidade >= 0)
                    sprite.setRotation(sprite.getRotation() - 10);
                else
                    sprite.setRotation(sprite.getRotation() + 10);
                coordenadaX = coordenadaX + velocidade;
                coordenadaY = coordenadaY + balao.getCoordenadaY() + balao.getTamanho() * 5 / 6 ;
            }else{
                if(naoSalvo){
                    xAlvo = balao.getCoordenadaX();
                    naoSalvo = false;
                    a = (float) ((- (coordenadaY - balao.getCoordenadaY()))/(Math.pow(coordenadaX - balao.getCoordenadaX(), 2)));
                    b = (2*(coordenadaY - balao.getCoordenadaY()))/(coordenadaX - balao.getCoordenadaX());
                    this.velocidade = (xAlvo - coordenadaX)/(100 - (6*cam.position.y)/(Gdx.graphics.getHeight()/2));
                }
                coordenadaY = (float) (a* Math.pow(coordenadaX - xAlvo,2) + b*(coordenadaX - xAlvo));

                sprite.setRotation((float) (Math.toDegrees(Math.atan(2*a*(coordenadaX - xAlvo) + b)) + ajusteRotacao));
                coordenadaX = coordenadaX + velocidade;
                coordenadaY = coordenadaY + balao.getCoordenadaY();
            }
        }
        if (velocidade > 0)
            sprite.setPosition(coordenadaX + Gdx.graphics.getWidth()/15, coordenadaY);
        else
            sprite.setPosition(coordenadaX, coordenadaY);
    }

    public int getTamanho() {
        return tamanho;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setColidiu(boolean colidiu){
        this.colidiu = colidiu;
    }

    public void setNaoSalvo(boolean naoSalvo) {
        this.naoSalvo = naoSalvo;
    }
}
