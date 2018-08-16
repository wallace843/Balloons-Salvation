package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import balloons.recursos.BalloonsImagens;
import balloons.recursos.BalloonsSons;
import balloons.util.BalloonsValores;

public class Aviao extends BalloonsObjeto {
    private float velocidade;
    private int alturaAviao;
    private boolean naoSalvo;
    private float xAlvo;
    private Sprite aviao;
    private float a;
    private float b;
    private boolean colidiu;
    private float deltaTime;
    private Circle cirAviao;
    private Circle cirBalao;

    public Aviao(float posicaoX, float posicaoY){
        cirAviao = new Circle();
        cirBalao = new Circle();
        this.colidiu = false;
        this.naoSalvo = true;
        this.aviao = new Sprite(BalloonsImagens.imagem.aviaoTexture);
        this.aviao.setSize(BalloonsValores.LARG_TELA/3, BalloonsValores.LARG_TELA/3);
        this.aviao.setX(posicaoX);
        if(aviao.getX() < Gdx.graphics.getWidth()/2)
            aviao.setX(-aviao.getWidth());
        this.aviao.setOriginCenter();
        this.aviao.setPosition(aviao.getX(),posicaoY);
        this.alturaAviao = Gdx.graphics.getHeight()*4/10;
        if(aviao.getX() < Gdx.graphics.getWidth()/2){
            this.aviao.setFlip(true,false);
        }
        this.deltaTime = 0f;
    }

    @Override
    public void movimentar(SpriteBatch batch, Balao balao) {
        if (balao.getCoordenadaY() <= aviao.getY() + aviao.getHeight() &&
                balao.getCoordenadaY() + Gdx.graphics.getHeight() >= aviao.getY() &&
                balao.getCoordenadaY() + Gdx.graphics.getHeight() - aviao.getY() > alturaAviao) {
            if (colidiu) {
                BalloonsSons.som.audioJato.stop();
                if (naoSalvo) {
                    xAlvo = aviao.getX();
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
                aviao.setY((float) ((a * Math.pow(aviao.getX() - xAlvo, 2)) / 10000000 + b * (aviao.getX() - xAlvo)));
                if (velocidade >= 0)
                    aviao.setRotation(aviao.getRotation() - 10);
                else
                    aviao.setRotation(aviao.getRotation() + 10);
                aviao.setX(aviao.getX() + velocidade);
                aviao.setY(aviao.getY() + balao.getCoordenadaY() + balao.getTamanho() * 5 / 6);
            } else {
                if (deltaTime == 0) {
                    BalloonsSons.som.audioJato.play();
                    deltaTime++;
                }
                if (naoSalvo) {
                    xAlvo = balao.getCoordenadaX();
                    naoSalvo = false;
                    a = (float) ((-(aviao.getY() - balao.getCoordenadaY())) / (Math.pow(aviao.getX() - balao.getCoordenadaX(), 2)));
                    b = (2 * (aviao.getY() - balao.getCoordenadaY())) / (aviao.getX() - balao.getCoordenadaX());
                    this.velocidade = (xAlvo - aviao.getX()) / (100 - (6 * balao.getCoordenadaY() + (Gdx.graphics.getHeight() / 2
                            - (Gdx.graphics.getWidth() * 50) / 720)) / (Gdx.graphics.getHeight() / 2));
                }
                aviao.setY((float) (a * Math.pow(aviao.getX() - xAlvo, 2) + b * (aviao.getX() - xAlvo)));
                aviao.setRotation((float) (Math.toDegrees(Math.atan(2 * a * (aviao.getX() - xAlvo) + b))));
                aviao.setX(aviao.getX() + velocidade);
                aviao.setY(aviao.getY() + balao.getCoordenadaY());
            }
        }
        if (!colidiu) {
            colidiu = colisao(balao);
        }
        aviao.setPosition(aviao.getX(), aviao.getY());
        renderizar(batch);
    }

    @Override
    public void renderizar(SpriteBatch batch) { aviao.draw(batch); }

    @Override
    public boolean colisao(Balao balao) {
        cirBalao.setRadius(balao.getTamanho()/5);
        cirAviao.setRadius(aviao.getWidth()/17);
        cirAviao.setPosition(aviao.getWidth()/2 + aviao.getX(),aviao.getWidth()/5 + aviao.getWidth()/17 + aviao.getY());
        cirBalao.setPosition(balao.getTamanho()/2 + balao.getCoordenadaX(), balao.getTamanho()*3/5 + balao.getCoordenadaY());
        if(cirAviao.overlaps(cirBalao)){
            if(BalloonsValores.VIDA == 0) {
                BalloonsSons.som.audioBalaoEstouro.play();
            }
            BalloonsValores.VIDA--;
            BalloonsSons.som.audioBalaoColisao.play();
            naoSalvo = true;
            return true;
        }
        return false;
    }
}
