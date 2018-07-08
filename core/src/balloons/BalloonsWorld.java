package balloons;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BalloonsWorld implements ApplicationListener {
	private SpriteBatch batch;
	private Sprite[] sprites;
	private BalloonsController controller;
	private OrthographicCamera camera;
	private float cameraY;
	private float cameraX;

    @Override
    public void create() {
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
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Sprite s: sprites){
			s.draw(batch);
		}
		batch.end();
		camera.position.set(cameraX,++cameraY,0);
		controller.atualizar(camera);
		sprites = controller.gerarSprites();
		camera.update();
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
