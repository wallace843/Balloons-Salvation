package balloons.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Passaro {
    private int tamanho;
    private float coordenadaX;
    private float coordenadaY;
    private Sprite sprite;

    public Passaro(Texture texture, float posicaoX, float posicaoY){
        this.tamanho = 200;
        this.sprite = new Sprite(texture);
        this.coordenadaX = posicaoX;;
        this.coordenadaY = posicaoY;
        this.sprite.setSize(tamanho+1,tamanho);
        if(coordenadaX + tamanho > Gdx.graphics.getWidth())
            coordenadaX = Gdx.graphics.getWidth() - tamanho - 1;
        this.sprite.setPosition(coordenadaX,coordenadaY);
    }

    public Sprite getSprite() {
        return sprite;
    }
}
