package balloons.telas.jogo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Locale;
import balloons.recursos.BalloonsImagens;
import balloons.recursos.BalloonsSons;
import balloons.telas.BalloonsFim;
import balloons.telas.BalloonsMudanca;
import balloons.util.BalloonsValores;

public class BalloonsJogo implements Screen {
	private SpriteBatch batch;
	private BalloonsController controller;
	private OrthographicCamera camera;
	private float cameraX;
	private Sprite ballonsSalvatioLogo;
	private BitmapFont fonte;
	private Game game;
	private String nomeNivel;
	private Texture circulo;
	private Texture bolha;
	private Texture balao;
	private Texture back;

    public BalloonsJogo(Game game, String nomeNivel) {
		BalloonsImagens.imagem.init();
		BalloonsSons.som.init();
		BalloonsSons.som.musica1.play();
    	if(nomeNivel.equals("NIVEL 1"))
			this.controller = new BalloonsController("nivel_1.png");
		else if(nomeNivel.equals("NIVEL 2"))
			this.controller = new BalloonsController("nivel_2.png");
		else if(nomeNivel.equals("NIVEL 3"))
			this.controller = new BalloonsController("nivel_3.png");
		this.nomeNivel = nomeNivel;
		this.ballonsSalvatioLogo = new Sprite(BalloonsImagens.imagem.ballonsSalvatioLogo);
		this.game = game;
        this.cameraX = Gdx.graphics.getWidth()/2;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(cameraX, Gdx.graphics.getHeight()/2, 0);
        this.camera.update();
		this.fonte = new BitmapFont(Gdx.files.internal("fonts/teste.fnt"));
		this.fonte.setColor(Color.GOLD);
		this.circulo = new Texture("circulo.png");
		this.bolha = new Texture("bolha.png");
		this.balao = new Texture("balao.png");
		this.back = new Texture("back.png");
	}

	@Override
	public void render(float deltaTime) {
		if(controller.fimJogo()){
			Gdx.gl.glClearColor(0x00 / 255.0f, 0x00 / 255.0f, 0x00 / 255.0f, 0xff / 255.0f);
		}else
    		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0x00 / 255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		controller.atualizar(batch);
		if(controller.inicioJogo()){
			//batch.draw(back,camera.position.x-50, camera.position.y-10,250,70);
			fonte.draw(batch, "SALVE O BALAO!",camera.position.x, camera.position.y,0,1,false);
			fonte.setColor(Color.BLACK);
		}
		if(controller.fimJogo()){
			BalloonsSons.som.musica1.stop();
			game.setScreen(new BalloonsFim(game));
		}else if(controller.balaoSalvo()){
            ballonsSalvatioLogo.setSize(BalloonsValores.LARG_TELA, BalloonsValores.LARG_TELA);
            ballonsSalvatioLogo.setPosition(Gdx.graphics.getWidth()/2 - ballonsSalvatioLogo.getWidth()/2,
                    10* BalloonsValores.LARG_TELA + BalloonsValores.ALT_TELA/2 - ballonsSalvatioLogo.getHeight()/2);
            ballonsSalvatioLogo.draw(batch);
            controller.atualizar(batch);
            if(controller.getBalao().getCoordenadaY() > BalloonsValores.LARG_TELA*10 + BalloonsValores.ALT_TELA) {
                BalloonsSons.som.musica1.stop();
                dispose();
                game.setScreen(new BalloonsMudanca(game, nomeNivel));
            }
        }
		if (!controller.balaoSalvo()) {
            camera.position.set(cameraX, controller.getBalao().getCoordenadaY() + Gdx.graphics.getHeight() / 2 - 50, 0);
            renderMoldura();
        }
		batch.end();
		camera.update();
	}

	private void renderMoldura() {
    	float raio = 250*BalloonsValores.GAME_ESCALA;
    	float t = 50;
    	batch.draw(circulo,camera.position.x-BalloonsValores.LARG_TELA/2-raio,
				camera.position.y-BalloonsValores.ALT_TELA/2-raio, 2*raio,2*raio);
		batch.draw(circulo,camera.position.x+BalloonsValores.LARG_TELA/2-raio,
				camera.position.y-BalloonsValores.ALT_TELA/2-raio, 2*raio,2*raio);
		batch.draw(back,camera.position.x-BalloonsValores.LARG_TELA/2+5,
                camera.position.y+BalloonsValores.ALT_TELA/2-(60+t),t*7,t+20);
		fonte.draw(batch,String.format(Locale.getDefault(),"%09d",BalloonsValores.SCORE),
                camera.position.x-BalloonsValores.LARG_TELA/2+t+40,
				camera.position.y+BalloonsValores.ALT_TELA/2-60);
		batch.draw(bolha,camera.position.x-BalloonsValores.LARG_TELA/2+25,
                camera.position.y+BalloonsValores.ALT_TELA/2-(50+t),t,t);
        batch.draw(back,camera.position.x+BalloonsValores.LARG_TELA/2-t*6-40,
                camera.position.y+BalloonsValores.ALT_TELA/2-(60+t),t*6+30,t+20);
        fonte.draw(batch,String.format(Locale.getDefault(),"%09d",BalloonsValores.VIDA),
                camera.position.x+BalloonsValores.LARG_TELA/2-6*t-25,
                camera.position.y+BalloonsValores.ALT_TELA/2-60);
        batch.draw(balao,camera.position.x+BalloonsValores.LARG_TELA/2-35-t,
                camera.position.y+BalloonsValores.ALT_TELA/2-(65+t),t+30,t+30);
    }

	@Override
	public void show() {

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
	public void dispose () {
        batch.dispose();
        fonte.dispose();
        BalloonsSons.som.dispose();
        BalloonsImagens.imagem.dispose();
	}
}
