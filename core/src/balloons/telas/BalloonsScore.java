package balloons.telas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Locale;

import balloons.recursos.BalloonsImagens;
import balloons.util.BalloonsValores;

public class BalloonsScore implements Screen {
    private Game game;
    private Stage estagio;
    private int intervaloConstru;
    private Button botaoSalvar;
    private BitmapFont font;

    public BalloonsScore(Game game) {
        this.game = game;
        this.font = new BitmapFont(Gdx.files.internal("fonts/teste.fnt"));
        BalloonsImagens.imagem.init();
    }

    private void reconstruir(){
        Skin skin;
        Image background;
        skin = new Skin();
        skin.add("botao",BalloonsImagens.imagem.botao_salvar);
        skin.add("back_input",BalloonsImagens.imagem.back_entrada);
        skin.add("cursor",BalloonsImagens.imagem.balloon_cursor);
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.GOLD;
        Table tableBack = new Table();
        background = new Image(BalloonsImagens.imagem.backgroundGeral);
        tableBack.add(background);
        Table tableScore = new Table();
        tableScore.center();
        Label labelScore = new Label("Score " + BalloonsValores.SCORE, style);
        tableScore.add(labelScore);
        tableScore.row();
        TextField.TextFieldStyle fieldStyle = new TextField.TextFieldStyle(font,Color.BLACK,skin.getDrawable("cursor"),skin.getDrawable("back_input"),skin.getDrawable("back_input"));
        final TextField entrada = new TextField("Seu nickname", fieldStyle);
        tableScore.add(entrada).width(500).height(100);
        botaoSalvar = new Button(skin.getDrawable("botao"));
        botaoSalvar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                botaoSalvar.setDisabled(true);
                FileHandle registroScores = Gdx.files.local("maioresPontuacoes.txt");
                registroScores.writeString(String.format(Locale.getDefault(),"%09d",BalloonsValores.SCORE)+" "+entrada.getText()+" "+"\n",true);
                dispose();
                game.setScreen(new BalloonsPontuacoes(game));
            }
        });
        tableScore.row();
        tableScore.add(botaoSalvar);
        estagio.clear();
        Stack pilha = new Stack();
        estagio.addActor(pilha);
        pilha.setSize(BalloonsValores.LARG_TELA, BalloonsValores.ALT_TELA);
        pilha.add(tableBack);
        pilha.add(tableScore);

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
        BalloonsImagens.imagem.dispose();
    }
}
