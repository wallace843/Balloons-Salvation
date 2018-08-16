package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

import balloons.recursos.BalloonsImagens;
import balloons.recursos.BalloonsSons;
import balloons.util.BalloonsValores;

public class Cobra extends BalloonsObjeto {
    private float velocidade;
    private int alturaCobra;
    private boolean naoSalvo;
    private float xAlvo;
    private Sprite cobra;
    private float a;
    private float b;
    private boolean colidiu;
    private float deltaTime;
    private Circle cirCobra;
    private Circle cirBalao;

    public Cobra(float posicaoX, float posicaoY){
        cirCobra = new Circle();
        cirBalao = new Circle();
        this.colidiu = false;
        this.naoSalvo = true;
        this.cobra = new Sprite(BalloonsImagens.imagem.cobraTexure);
        this.cobra.setSize(BalloonsValores.LARG_TELA/3, BalloonsValores.LARG_TELA/3);
        this.cobra.setX(posicaoX);
        if(cobra.getX() < Gdx.graphics.getWidth()/2)
            cobra.setX(-cobra.getWidth());
        this.cobra.setOriginCenter();
        this.cobra.setPosition(cobra.getX(),posicaoY);
        this.alturaCobra = Gdx.graphics.getHeight()*4/10;
        if(cobra.getX() < Gdx.graphics.getWidth()/2){
            this.cobra.setFlip(true,false);
        }
        this.deltaTime = 0f;
    }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao) {
        if (balao.getCoordenadaY() <= cobra.getY() + cobra.getHeight() &&
                balao.getCoordenadaY() + Gdx.graphics.getHeight() >= cobra.getY() &&
                balao.getCoordenadaY() + Gdx.graphics.getHeight() - cobra.getY() > alturaCobra) {
            if (colidiu) {
                BalloonsSons.som.audioJato.stop();
                if (naoSalvo) {
                    xAlvo = cobra.getX();
                    naoSalvo = false;
                    a = -20000000 / Gdx.graphics.getWidth();
                    if (velocidade <= 0) {
                        b = -2;
                        velocidade = -15;
                    } else {
                        b = 2;
                        velocidade = 15;
                    }
                }
                cobra.setY((float) ((a * Math.pow(cobra.getX() - xAlvo, 2)) / 10000000 + b * (cobra.getX() - xAlvo)));
                if (velocidade >= 0)
                    cobra.setRotation(cobra.getRotation() - 10);
                else
                    cobra.setRotation(cobra.getRotation() + 10);
                cobra.setX(cobra.getX() + velocidade);
                cobra.setY(cobra.getY() + balao.getCoordenadaY() + balao.getTamanho() * 5 / 6);
            } else {
                if (deltaTime == 0) {
                    BalloonsSons.som.audioJato.stop();
                    BalloonsSons.som.audioJato.play();
                    deltaTime++;
                }
                if (naoSalvo) {
                    xAlvo = balao.getCoordenadaX();
                    naoSalvo = false;
                    a = (float) ((-(cobra.getY() - balao.getCoordenadaY())) / (Math.pow(cobra.getX() - balao.getCoordenadaX(), 2)));
                    b = (2 * (cobra.getY() - balao.getCoordenadaY())) / (cobra.getX() - balao.getCoordenadaX());
                    this.velocidade = (xAlvo - cobra.getX()) / (100 - (6 * balao.getCoordenadaY() + (Gdx.graphics.getHeight() / 2
                            - (Gdx.graphics.getWidth() * 50) / 720)) / (Gdx.graphics.getHeight() / 2));
                }
                cobra.setY((float) (a * Math.pow(cobra.getX() - xAlvo, 2) + b * (cobra.getX() - xAlvo)));
                cobra.setRotation((float) (Math.toDegrees(Math.atan(2 * a * (cobra.getX() - xAlvo) + b))));
                cobra.setX(cobra.getX() + velocidade);
                cobra.setY(cobra.getY() + balao.getCoordenadaY());
            }
        }
        if (!colidiu) {
            colidiu = colisao(balao);
        }
        cobra.setPosition(cobra.getX(), cobra.getY());
        renderizar(batch);
    }

    @Override
    public void renderizar(SpriteBatch batch) { cobra.draw(batch); }

    @Override
    public boolean colisao(Balao balao) {
        cirBalao.setRadius(balao.getTamanho()/5);
        cirCobra.setRadius(cobra.getWidth()/17);
        cirCobra.setPosition(cobra.getWidth()/2 + cobra.getX(), cobra.getWidth()/5 + cobra.getWidth()/17 + cobra.getY());
        cirBalao.setPosition(balao.getTamanho()/2 + balao.getCoordenadaX(), balao.getTamanho()*3/5 + balao.getCoordenadaY());
        if(cirCobra.overlaps(cirBalao)){
            if(BalloonsValores.VIDA == 0) {
                BalloonsSons.som.audioBalaoEstouro.stop();
                BalloonsSons.som.audioBalaoEstouro.play();
            }
            BalloonsValores.VIDA--;
            BalloonsSons.som.audioBalaoColisao.stop();
            BalloonsSons.som.audioBalaoColisao.play();
            naoSalvo = true;
            return true;
        }
        return false;
    }

    public void setColidiu(boolean colidiu){
        this.colidiu = colidiu;
    }

    public void setNaoSalvo(boolean naoSalvo) {
        this.naoSalvo = naoSalvo;
    }
}
