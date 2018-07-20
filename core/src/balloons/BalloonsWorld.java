package balloons;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sun.net.ssl.SSLPermission;

import balloons.Util.BalloonsConstants;
import balloons.objetos.BalloonsObjetos;

public class BalloonsWorld implements ApplicationListener {
	private SpriteBatch batch;
	private BalloonsObjetos[] objetos;
	private BalloonsController controller;
	private OrthographicCamera camera;
	private float cameraY;
	private float cameraX;
	private ShapeRenderer barraVida;
	private ShapeRenderer barrafundo;
	private ShapeRenderer c_direito;
	private ShapeRenderer c_esquerdo;
	private Sprite ballonsSalvatioLogo;
	private BitmapFont fonte;
	private float nivelVida;

    @Override
    public void create() {
		this.ballonsSalvatioLogo = new Sprite(new Texture("BalloonsSalvation.png"));
        this.barraVida = new ShapeRenderer();
        this.barrafundo = new ShapeRenderer();
        this.c_direito = new ShapeRenderer();
        this.c_esquerdo = new ShapeRenderer();
        this.cameraY = Gdx.graphics.getHeight()/2;
        this.cameraX = Gdx.graphics.getWidth()/2;
        this.batch = new SpriteBatch();
        this.controller = new BalloonsController();
        this.objetos = controller.gerarObjetos();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(cameraX, cameraY, 0);
        this.camera.update();
        this.fonte = new BitmapFont();
    }

	@Override
	public void render() {
		if(controller.fimJogo()){
			Gdx.gl.glClearColor(0x00 / 255.0f, 0x00 / 255.0f, 0x00 / 255.0f, 0xff / 255.0f);
		}else
    		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0x00 / 255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        if(controller.fimJogo())
        	controller.getBalao().getSprite().setSize(0,0);
		batch.begin();
		for(BalloonsObjetos o: objetos){
			o.getSprite().draw(batch);
		}

		if(controller.inicioJogo()){
			fonte.setColor(Color.GOLD);
			fonte.getData().setScale(2);
			fonte.draw(batch, "PROTEJA O BALÃO!",camera.position.x, camera.position.y,0,1,false);
		}

		if(controller.fimJogo()){
			fonte.setColor(1,1,1,1);
			fonte.getData().setScale(2);
			fonte.draw(batch, "FIM DE JOGO",camera.position.x, camera.position.y,0,1,false);
		}

		batch.end();
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);
		if(!controller.fimJogo()){
            if(controller.balaoSalvo()){
            	ballonsSalvatioLogo.setSize(Gdx.graphics.getWidth()/1.2f, Gdx.graphics.getWidth()/1.2f);
            	ballonsSalvatioLogo.setPosition(Gdx.graphics.getWidth()/2 - ballonsSalvatioLogo.getWidth()/2, cameraY - ballonsSalvatioLogo.getHeight()/2);
                batch.begin();
                ballonsSalvatioLogo.draw(batch);
                batch.end();
				controller.atualizar(camera);
            }else{
                camera.position.set(cameraX,objetos[objetos.length - 1].getSprite().getY() + Gdx.graphics.getHeight()/2 - 50, 0);
                controller.atualizar(camera);
                renderMoldura();
            }
		}
		Gdx.gl.glDisable(GL20.GL_BLEND);
		objetos = controller.gerarObjetos();
		camera.update();
	}

	private void renderMoldura() {
		barrafundo.begin(ShapeRenderer.ShapeType.Filled);
		barrafundo.rect(50 , BalloonsConstants.ALT_TELA - 150, BalloonsConstants.LARG_TELA - 100, 100);
		barrafundo.setColor(new Color(0 << 24 | 0 << 16 | 0 << 8 | 0xff));
		barrafundo.end();

		nivelVida = (BalloonsConstants.LARG_TELA-100)*(controller.getBalao().getVida()/(BalloonsConstants.LARG_TELA/4f));
        barraVida.begin(ShapeRenderer.ShapeType.Filled);
        barraVida.rect(50 + BalloonsConstants.LARG_TELA - 100 - nivelVida, BalloonsConstants.ALT_TELA - 150, nivelVida, 100);
        float r, g;
        r = ((Gdx.graphics.getWidth()/4f - controller.getBalao().getVida())/(Gdx.graphics.getWidth()/4f))*255f;
        g = (controller.getBalao().getVida()/(Gdx.graphics.getWidth()/4f))*255f;
        barraVida.setColor(new Color((int)r << 24 | (int)g << 16 | 0 << 8 | 0xff));
        barraVida.end();

        c_esquerdo.begin(ShapeRenderer.ShapeType.Filled);
        c_esquerdo.circle(0,0, 250);
        c_esquerdo.setColor(new Color(0f,0f,0f,0.5f));
        c_esquerdo.end();

		c_direito.begin(ShapeRenderer.ShapeType.Filled);
		c_direito.circle(BalloonsConstants.LARG_TELA,0, 250);
		c_direito.setColor(new Color(0f,0f,0f,0.5f));
		c_direito.end();
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
	public void dispose () {
		batch.dispose();
	}
}
