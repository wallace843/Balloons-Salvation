package balloons;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import balloons.recursos.BalloonsImagens;
import balloons.telas.BalloonsMenu;

public class BalloonsPrincipal extends Game{
    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        setScreen(new BalloonsMenu(this));
    }
}
