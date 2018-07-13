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

public class BalloonsWorld implements ApplicationListener {
	private SpriteBatch batch;
	private Sprite[] sprites;
	private BalloonsController controller;
	private OrthographicCamera camera;
	private float cameraY;
	private float cameraX;
	private Sprite miniBalao;
	private ShapeRenderer forma;
	private Sprite ballonsSalvatioLogo;

    @Override
    public void create() {
		this.ballonsSalvatioLogo = new Sprite(new Texture("BalloonsSalvation.png"));
        this.forma = new ShapeRenderer();
        this.miniBalao = new Sprite(new Texture("balao.png"));
        miniBalao.setSize(Gdx.graphics.getWidth()/18,Gdx.graphics.getWidth()/18);
        miniBalao.setColor(Color.GOLD);
        this.cameraY = Gdx.graphics.getHeight()/2;
        this.cameraX = Gdx.graphics.getWidth()/2;
        this.batch = new SpriteBatch();
        this.controller = new BalloonsController();
        this.sprites = controller.gerarSprites();
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.position.set(cameraX, cameraY, 0);
        this.camera.update();
    }

	@Override
	public void render() {
		if(controller.fimJogo()){
			Gdx.gl.glClearColor(0x00 / 255.0f, 0x00 / 255.0f, 0x00 / 255.0f, 0xff / 255.0f);
		}else
    		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        if(controller.fimJogo())
        	controller.getBalao().getSprite().setSize(0,0);
		batch.begin();
		for(Sprite s: sprites){
			s.draw(batch);
		}
		if(controller.fimJogo()){
			BitmapFont textoFim = new BitmapFont();
			textoFim.setColor(1,1,1,1);
			textoFim.getData().setScale(2);
			textoFim.draw(batch, "FIM DE JOGO",camera.position.x, camera.position.y,0,1,true);
		}

		batch.end();

		if(!controller.fimJogo()){
            if(controller.balaoSalvo()){
            	ballonsSalvatioLogo.setSize(Gdx.graphics.getWidth()/1.2f, Gdx.graphics.getWidth()/1.2f);
            	ballonsSalvatioLogo.setPosition(Gdx.graphics.getWidth()/2 - ballonsSalvatioLogo.getWidth()/2, cameraY - ballonsSalvatioLogo.getHeight()/2);
                batch.begin();
                ballonsSalvatioLogo.draw(batch);
                batch.end();
				controller.atualizar(camera);
            }else{
                camera.position.set(cameraX, cameraY++, 0);
                controller.atualizar(camera);
                renderVida(batch);
            }
		}
		sprites = controller.gerarSprites();
		camera.update();
	}

	private void renderVida(SpriteBatch batch) {
    	batch.begin();
        miniBalao.setPosition(cameraX - Gdx.graphics.getWidth() / 2 + 50, cameraY + Gdx.graphics.getHeight() / 2 - miniBalao.getHeight() - 50);
        miniBalao.draw(batch);
        batch.end();
        forma.begin(ShapeRenderer.ShapeType.Filled);
        forma.rect(50 + miniBalao.getWidth(), Gdx.graphics.getHeight() - 50 - miniBalao.getWidth()*5/6, controller.getBalao().getVida(), miniBalao.getWidth()*5/6);
        float r, g, b, a;
        r = ((Gdx.graphics.getWidth()/4f - controller.getBalao().getVida())/(Gdx.graphics.getWidth()/4f))*255f;
        g = (controller.getBalao().getVida()/(Gdx.graphics.getWidth()/4f))*255f;
        b = 0;
        forma.setColor(new Color((int)r << 24 | (int)g << 16 | (int)b << 8 | 0xff));
        forma.end();
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
