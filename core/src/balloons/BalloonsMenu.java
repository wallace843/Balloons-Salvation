package balloons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import balloons.Util.BalloonsConstants;

public class BalloonsMenu implements Screen {
    private Game game;
    private Stage estagio;
    private Image imagemFundo;
    private Button botaoJogar;
    private Button botaoScore;
    private Button botaoOpcoes;
    private float intervaloConstru;
    private Skin skin;

    public BalloonsMenu(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        estagio = new Stage();
        Gdx.input.setInputProcessor(estagio);
        intervaloConstru = 5;
    }

    private void reconstruir(){
        skin = new Skin();
        skin.add("botao_jogar",new Texture("botoes_balloon/botao_jogar.png"));
        skin.add("botao_score",new Texture("botoes_balloon/botao_score.png"));
        skin.add("botao_opcoes",new Texture("botoes_balloon/botao_opcoes.png"));
        Table construBackground = new Table();
        imagemFundo = new Image(new Texture("background.png"));
        construBackground.add(imagemFundo);
        Table botoes = new Table();
        botoes.center().bottom();
        botoes.padBottom(280);
        botaoJogar = new Button(skin.getDrawable("botao_jogar"));
        botaoJogar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                botaoJogar.setDisabled(true);
                game.setScreen(new BalloonsJogo(game));
            }
        });
        botoes.add(botaoJogar);
        botaoScore = new Button(skin.getDrawable("botao_score"));
        botaoScore.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        botoes.row();
        botoes.add(botaoScore);
        botaoOpcoes = new Button(skin.getDrawable("botao_opcoes"));
        botaoOpcoes.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
        botoes.row();
        botoes.add(botaoOpcoes);
        estagio.clear();
        Stack pilha = new Stack();
        estagio.addActor(pilha);
        pilha.setSize(BalloonsConstants.LARG_TELA,BalloonsConstants.ALT_TELA);
        pilha.add(construBackground);
        pilha.add(botoes);
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

    }
}
