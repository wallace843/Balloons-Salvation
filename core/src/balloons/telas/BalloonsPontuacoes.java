package balloons.telas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Arrays;
import java.util.Collections;

import balloons.util.BalloonsValores;

public class BalloonsPontuacoes implements Screen {
    private BitmapFont font;
    private Game game;
    private Stage estagio;
    private int intervaloConstru;
    private Skin skin;
    private Image background;
    private Button botaoMenu;
    private ScrollPane scrollPane;

    public BalloonsPontuacoes(Game game) {
        this.game = game;
        this.font = new BitmapFont(Gdx.files.internal("fonts/teste.fnt"));
    }

    public void reconstruir(){

    }

    @Override
    public void show() {
        estagio = new Stage();
        Gdx.input.setInputProcessor(estagio);
        skin = new Skin();
        skin.add("botao",new Texture("botoes_balloon/botao_menu.png"));
        skin.add("sel", new Texture("botoes_balloon/back_entrada.png"));
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;
        style.fontColor = Color.GOLD;
        Table tableBack = new Table();
        background = new Image(new Texture("backgroundGeral.png"));
        tableBack.add(background);
        Table table = new Table();
        Label titulo = new Label("SCORES", style);
        table.padTop(50);
        table.padBottom(200);
        table.top();
        table.add(titulo);
        table.row();
        List.ListStyle listStyle = new List.ListStyle(font,Color.GOLD,Color.GOLD, skin.getDrawable("sel"));
        List itens = new List<String>(listStyle);
        FileHandle file = Gdx.files.local("maioresPontuacoes.txt");
        String conteudo = file.readString();
        String registros[] = conteudo.split("\\r?\\n");
        java.util.List<String> tmp = Arrays.asList(registros);
        Collections.sort(tmp,Collections.<String>reverseOrder());
        registros = tmp.toArray(new String[0]);
        int index = 1;
        for(String s: registros){
            registros[index - 1] = index + ". " + s;
            index++;
        }
        itens.setItems(registros);
        scrollPane = new ScrollPane(itens);
        table.add(scrollPane);
        Table tableB = new Table();
        tableB.bottom();
        botaoMenu = new Button(skin.getDrawable("botao"));
        botaoMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                botaoMenu.setDisabled(true);
                game.setScreen(new BalloonsMenu(game));
            }
        });
        tableB.padBottom(50);
        tableB.add(botaoMenu);
        estagio.clear();
        Stack pilha = new Stack();
        estagio.addActor(pilha);
        pilha.setSize(BalloonsValores.LARG_TELA, BalloonsValores.ALT_TELA);
        pilha.add(tableBack);
        pilha.add(table);
        pilha.add(tableB);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        estagio.act(delta);
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
