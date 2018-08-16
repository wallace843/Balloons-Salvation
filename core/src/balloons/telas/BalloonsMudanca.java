package balloons.telas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

import balloons.util.BalloonsValores;

public class BalloonsMudanca implements Screen{
    private Game game;
    private String nomeNivel;
    private Stage estagio;
    private int intervaloConstru;
    private Skin skin;
    private Image background;
    private Button botaoProximo;
    private BitmapFont font;

    public BalloonsMudanca(Game game, String nomeNivel) {
        this.nomeNivel = nomeNivel;
        this.game = game;
        this.font = new BitmapFont(Gdx.files.internal("fonts/teste.fnt"));
    }

    public void reconstruir(){
        skin = new Skin();
        skin.add("botao",new Texture("botoes_balloon/botao_proximo.png"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.GOLD;
        Table tableBack = new Table();
        background = new Image(new Texture("backgroundGeral.png"));
        tableBack.add(background);
        Table tableNome = new Table();
        Label nivel = new Label(nomeNivel, style);
        Label concluido = new Label("CONCLUIDO!", style);
        tableNome.center();
        tableNome.add(nivel);
        tableNome.row();
        tableNome.add(concluido);
        botaoProximo = new Button(skin.getDrawable("botao"));
        botaoProximo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                botaoProximo.setDisabled(true);
                if(nomeNivel.equals("NIVEL 1"))
                    game.setScreen(new BalloonsJogo(game,"NIVEL 2"));
                else if(nomeNivel.equals("NIVEL 2"))
                    game.setScreen(new BalloonsJogo(game,"NIVEL 3"));
                else if(nomeNivel.equals("NIVEL 3"))
                    game.setScreen(new BalloonsJogo(game,"NIVEL 1"));
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
        font.dispose();
    }
}
