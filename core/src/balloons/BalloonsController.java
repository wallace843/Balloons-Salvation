package balloons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

import balloons.Util.BalloonsConstants;
import balloons.niveis.BallonsNivel1;
import balloons.objetos.Aviao;
import balloons.objetos.Balao;
import balloons.objetos.BalloonsObjetos;
import balloons.objetos.Nuvem;
import balloons.objetos.Passaro;
import balloons.objetos.Pipa;

public class BalloonsController {
    private Nuvem[] nuvens;
    private Pipa[] pipas;
    private Passaro[] passaros;
    private Aviao[] avioes;
    private Balao balao;
    private Circle colisaoBalao;
    private Circle colisaoInimigo;

    public BalloonsController(){
        Texture nuvemTexture = new Texture("nuvem.png");
        Texture balaoTexture = new Texture("balao.png");
        Random r = new Random();
        colisaoBalao = new Circle();
        colisaoInimigo = new Circle();
        this.nuvens = new Nuvem[r.nextInt(20) + 180];
        this.balao = new Balao(balaoTexture);
        for(int i = 0; i < nuvens.length; i++){
            nuvens[i] = new Nuvem(nuvemTexture);
        }
        BallonsNivel1 nivel1 = new BallonsNivel1();
        this.pipas = nivel1.getPipas();
        this.avioes = nivel1.getAvioes();
        this.passaros = nivel1.getPassaros();

    }

    public void atualizar(OrthographicCamera camera){
        atualizarBalao();
        atualizarNuvens(camera);
        atualizarAvioes(camera);
        atualizarPassaros();
        if(!fimJogo())
            checarColisao();
    }

    private void atualizarNuvens(OrthographicCamera cam){
        for(Nuvem n : nuvens){
            n.movimentar(cam);
        }
    }

    private void atualizarAvioes(OrthographicCamera camera){
        for (Aviao a : avioes){
            a.movimentar(camera, balao);
        }
    }

    private void atualizarPassaros(){
        for(Passaro p: passaros){
            p.movimentar(balao);
        }
    }

    private void atualizarBalao(){
        if(Gdx.input.isTouched()){
            float pressX = Gdx.input.getX();
            float pressY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if(Math.sqrt(Math.pow(pressX,2)+Math.pow(pressY,2)) < Gdx.graphics.getWidth()/2){
                balao.moverEsquerda();
            }else if(Math.sqrt(Math.pow(pressX - Gdx.graphics.getWidth(),2)+Math.pow(pressY,2)) < Gdx.graphics.getWidth()/2){
                balao.moverDireita();
            }else
                balao.moverCima();
        }else
            balao.moverCima();
    }

    private void checarColisao(){
        colisaoBalao.setRadius(balao.getTamanho()/5);
        colisaoInimigo.setRadius(avioes[0].getTamanho()/17);
        colisaoBalao.setPosition(balao.getTamanho()/2 + balao.getSprite().getX(), balao.getTamanho()*3/5 + balao.getSprite().getY());
        for(Aviao a : avioes){
            colisaoInimigo.setPosition(a.getTamanho()/2 + a.getSprite().getX(),a.getTamanho()/5 + a.getTamanho()/17 + a.getSprite().getY());
            if(colisaoInimigo.overlaps(colisaoBalao)){
                if(balao.getVida() - 20 < 0)
                    balao.setVida(0);
                else
                    balao.setVida(balao.getVida() - 15f);
                a.setColidiu(true);
                a.setNaoSalvo(true);
            }
        }
        colisaoInimigo.setRadius(15);
        for(Passaro p : passaros){
            colisaoInimigo.setPosition(p.getSprite().getWidth()/2 + p.getSprite().getX(),20 + p.getSprite().getY());
            if(colisaoInimigo.overlaps(colisaoBalao))
            balao.setVida(balao.getVida() - 1f);
        }
    }

    public boolean fimJogo(){
        return balao.getVida() == 0;
    }

    public boolean inicioJogo() { return balao.getSprite().getY() < BalloonsConstants.ALT_TELA/2;}

    public boolean balaoSalvo(){
        return balao.getSprite().getY() > Gdx.graphics.getWidth()*10;
    }

    public BalloonsObjetos[] gerarObjetos(){
        BalloonsObjetos[] objetos = new BalloonsObjetos[nuvens.length + avioes.length + pipas.length + passaros.length + 1];
        int i = 0;
        for(Nuvem n: nuvens){
            objetos[i] = n;
            i++;
        }
        for (Aviao a: avioes){
            objetos[i] = a;
            i++;
        }
        for (Pipa p: pipas){
            objetos[i] = p;
            i++;
        }
        for(Passaro pa: passaros){
            objetos[i] = pa;
            i++;
        }
        objetos[i] = balao;
        return objetos;
    }

    public Balao getBalao() {
        return balao;
    }
}
