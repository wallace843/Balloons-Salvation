package balloons.niveis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.LinkedList;
import java.util.List;
import balloons.objetos.Aviao;
import balloons.objetos.Passaro;
import balloons.objetos.Pipa;

public class BallonsNivel1 {
    private Pixmap mapa;
    private List<Pipa> pipas;
    private List<Passaro> passaros;
    private List<Aviao> avioes;

    public BallonsNivel1(){
        this.mapa = new Pixmap(Gdx.files.internal("nivel_1.png"));
        this.pipas = new LinkedList<Pipa>();
        this.passaros = new LinkedList<Passaro>();
        this.avioes = new LinkedList<Aviao>();
        init();
    }

    public enum PIXEL_TIPO{
        PIPA(255, 0, 0),
        AVIAO(255, 255, 255),
        PASSARO(0, 0, 255);

        private int cor;

        private PIXEL_TIPO (int r, int g, int b) {
            cor = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean mesmaCor (int cor) {
            return this.cor == cor;
        }

        public int getColor () {
            return cor;
        }
    }

    private void init(){
        int currentPixel;
        Texture aviaoTexture = new Texture("aviao_papel.png");
        Array<TextureRegion> pipaMov = pegarImageFrames("pipa1.png");
        Array<TextureRegion> passaroVoo = pegarImageFrames("passaro.png");
        Array<TextureRegion> passaroPara = pegarImageFrames("passaro2.png");
        Array<TextureRegion> passaroAtaq = pegarImageFrames("passaro3.png");
        float escala = Gdx.graphics.getWidth() / mapa.getWidth();
        for(int pixelY = 0; pixelY < mapa.getHeight(); pixelY++ ){
            for(int pixelX = 0; pixelX < mapa.getWidth(); pixelX++){
                currentPixel = mapa.getPixel(pixelX,pixelY);
                if(PIXEL_TIPO.AVIAO.mesmaCor(currentPixel)){
                    avioes.add(new Aviao(aviaoTexture, pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.PASSARO.mesmaCor(currentPixel)){
                    passaros.add(new Passaro(passaroVoo, passaroPara, passaroAtaq, pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.PIPA.mesmaCor(currentPixel)){
                    pipas.add(new Pipa(pipaMov,pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }
            }
        }
    }

    private Array<TextureRegion> pegarImageFrames(String local){
        Texture sheet =  new Texture(local);
        TextureRegion tmp[][] = TextureRegion.split(sheet, sheet.getWidth()/3, sheet.getHeight()/2);
        Array<TextureRegion> regions = new Array<TextureRegion>();
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++) {
                regions.add(tmp[i][j]);
            }
        }
        return regions;
    }

    public Pipa[] getPipas(){
        Pipa[] pipas = new Pipa[this.pipas.size()];
        for(int i = 0; i < this.pipas.size(); i++){
            pipas[i] = this.pipas.get(i);
        }
        return pipas;
    }

    public Aviao[] getAvioes(){
        Aviao[] avioes = new Aviao[this.avioes.size()];
        for(int i = 0; i < this.avioes.size(); i++){
            avioes[i] = this.avioes.get(i);
        }
        return avioes;
    }

    public Passaro[] getPassaros(){
        Passaro[] passaros = new Passaro[this.passaros.size()];
        for(int i = 0; i < this.passaros.size(); i++){
            passaros[i] = this.passaros.get(i);
        }
        return passaros;
    }
}
