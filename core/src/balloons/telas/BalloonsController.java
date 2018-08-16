package balloons.telas;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.List;
import balloons.objetos.BalloonsObjeto;
import balloons.recursos.BalloonsImagens;
import balloons.util.BalloonsValores;
import balloons.niveis.BallonsNivel;
import balloons.objetos.Balao;

public class BalloonsController {
    private List<BalloonsObjeto> objeto1;
    private List<BalloonsObjeto> objeto3;
    private List<BalloonsObjeto> objeto2;
    private List<BalloonsObjeto> objeto4;
    private List<BalloonsObjeto> objeto5;
    private Balao balao;

    public BalloonsController(String nomeNivel){
        BallonsNivel nivel = new BallonsNivel(nomeNivel);
        this.balao = new Balao();
        this.objeto1 = nivel.getObjeto1();
        this.objeto2 = nivel.getObjeto2();
        this.objeto3 = nivel.getObjeto3();
        this.objeto4 = nivel.getObjeto4();
        this.objeto5 = nivel.getObjeto5();
    }

    public void atualizar(SpriteBatch batch){
        atualizarFundo(batch);
        atualizarObjeto1(batch);
        atualizarObjeto2(batch);
        atualizarObjeto3(batch);
        atualizarObjeto4(batch);
        atualizarBalao(batch);
    }

    private void atualizarObjeto1(SpriteBatch batch){
        for(BalloonsObjeto o: objeto1){
            o.movimentar(batch,balao);
        }
    }

    private void atualizarObjeto2(SpriteBatch batch){
        for (BalloonsObjeto o : objeto2){
            o.movimentar(batch, balao);
        }
    }

    private void atualizarObjeto3(SpriteBatch batch){
        for(BalloonsObjeto o: objeto3){
            o.movimentar(batch, balao);
        }
    }

    private void atualizarObjeto4(SpriteBatch batch){
        for(BalloonsObjeto o: objeto4){
            o.movimentar(batch,balao);
        }
    }

    private void atualizarFundo(SpriteBatch batch){
        for(BalloonsObjeto o: objeto5){
            o.movimentar(batch,balao);
        }
    }

    private void atualizarBalao(SpriteBatch batch){ balao.movimentar(batch, balao); }

    public boolean fimJogo(){ return BalloonsValores.VIDA < 0; }

    public boolean inicioJogo(){ return balao.getCoordenadaY() < BalloonsValores.ALT_TELA/2; }

    public boolean balaoSalvo(){ return balao.getCoordenadaY() > BalloonsValores.LARG_TELA*10; }

    public Balao getBalao(){ return balao; }
}
