package balloons.telas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import balloons.recursos.BalloonsImagens;
import balloons.util.BalloonsValores;

public class BalloonsFim implements Screen {
    private Game game;
    private Stage estagio;
    private int intervaloConstru;
    private Button botaoProximo;
    private BitmapFont font;

    public BalloonsFim(Game game) {
        this.game = game;
        this.font = new BitmapFont(Gdx.files.internal("fonts/teste.fnt"));
        BalloonsImagens.imagem.init();
    }

    private void reconstruir(){
        Skin skin;
        Image background;
        skin = new Skin();
        skin.add("botao",BalloonsImagens.imagem.botao_proximo);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.GOLD;
        Table tableBack = new Table();
        background = new Image(BalloonsImagens.imagem.backgroundGeral);
        tableBack.add(background);
        Table tableNome = new Table();
        Label areafim = new Label("FIM DE JOGO!", style);
        tableNome.center();
        tableNome.add(areafim);
        botaoProximo = new Button(skin.getDrawable("botao"));
        botaoProximo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                botaoProximo.setDisabled(true);
                dispose();
                game.setScreen(new BalloonsScore(game));
            }
        });
        tableNome.row();
        tableNome.add(botaoProximo);
        estagio.clear();
        Stack pilha = new Stack();
        estagio.addActor(pilha);
        pilha.setSize(BalloonsValores.LARG_TELA, BalloonsValores.ALT_TELA);
        pilha.add(tableBack);
        pilha.add(tableNome);
    }

    @Override
    public void show() {
        estagio = new Stage();
        Gdx.input.setInputProcessor(estagio);
        intervaloConstru = 5;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(intervaloConstru == 5){
            reconstruir();
            intervaloConstru -=delta;
        }else if(intervaloConstru < 0)
            intervaloConstru = 5;
        else
            intervaloConstru -= delta;
        estagio.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.font.dispose();
        BalloonsImagens.imagem.dispose();
    }
}
