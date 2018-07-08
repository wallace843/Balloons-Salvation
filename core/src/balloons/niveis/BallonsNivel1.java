package balloons.niveis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.LinkedList;
import java.util.List;

import balloons.objetos.Aviao;
import balloons.objetos.Passaro;
import balloons.objetos.Pipa;

public class BallonsNivel1 {
    private Pixmap mapa;
    private List<Pipa> pipas;
    private List<Passaro> passaros;
    private List<Aviao> avioes;

    public BallonsNivel1(){
        this.mapa = new Pixmap(Gdx.files.internal("nivel_1"));
        this.pipas = new LinkedList<Pipa>();
        this.passaros = new LinkedList<Passaro>();
        this.avioes = new LinkedList<Aviao>();
    }
}
