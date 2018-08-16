package balloons.recursos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class BalloonsSons {
    public static final BalloonsSons som = new BalloonsSons();
    public Sound audioPassaro_asas;
    public Sound audioPassaro_canto;
    public Sound audioBalaoEstouro;
    public Sound audioJato;
    public Sound audioBalaoColisao;
    public Sound audioBalao_atrito;
    public Sound audioVento;
    public Sound audioBolhas;
    public Sound audioVida;
    public Sound audioMotor;
    public Music musica1;

    public void init(){
        this.audioVida = Gdx.audio.newSound(Gdx.files.internal("sons/vida.wav"));
        this.audioMotor = Gdx.audio.newSound(Gdx.files.internal("sons/motor.mp3"));
        this.audioBolhas = Gdx.audio.newSound(Gdx.files.internal("sons/bolhas.wav"));
        this.audioPassaro_asas = Gdx.audio.newSound(Gdx.files.internal("sons/passaro_asas.wav"));
        this.audioPassaro_canto = Gdx.audio.newSound(Gdx.files.internal("sons/passaro_canto.wav"));
        this.audioBalaoEstouro = Gdx.audio.newSound(Gdx.files.internal("sons/balao_estouro.wav"));
        this.audioJato = Gdx.audio.newSound(Gdx.files.internal("sons/jato.wav"));
        this.audioBalaoColisao = Gdx.audio.newSound(Gdx.files.internal("sons/balao_colisao.wav"));
        this.audioBalao_atrito = Gdx.audio.newSound(Gdx.files.internal("sons/balao_atrito.wav"));
        this.audioVento =  Gdx.audio.newSound(Gdx.files.internal("sons/vento.wav"));
        this.musica1 = Gdx.audio.newMusic(Gdx.files.internal("sons/so_alive.mp3"));
    }

    public void dispose(){
        audioPassaro_asas.dispose();
        audioPassaro_canto.dispose();
        audioBalaoEstouro.dispose();
        audioJato.dispose();
        audioBalaoColisao.dispose();
        audioBalao_atrito.dispose();
        audioVento.dispose();
        audioBolhas.dispose();
        audioVida.dispose();
        audioMotor.dispose();
        musica1.dispose();
    }
}
