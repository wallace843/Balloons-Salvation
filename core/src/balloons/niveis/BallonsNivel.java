package balloons.niveis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import balloons.objetos.Aviao;
import balloons.objetos.BalloonsObjeto;
import balloons.objetos.Bolha;
import balloons.objetos.Cobra;
import balloons.objetos.Drone;
import balloons.objetos.Floresta;
import balloons.objetos.Galho;
import balloons.objetos.Nuvem;
import balloons.objetos.Passaro;
import balloons.objetos.Pipa;
import balloons.objetos.Poste;
import balloons.objetos.Predio;
import balloons.recursos.BalloonsImagens;

public class BallonsNivel {
    private Pixmap mapa;
    private List<BalloonsObjeto> objeto1;
    private List<BalloonsObjeto> objeto3;
    private List<BalloonsObjeto> objeto2;
    private List<BalloonsObjeto> objeto4;
    private List<BalloonsObjeto> objeto5;

    public BallonsNivel(String nomeNivel){
        this.mapa = new Pixmap(Gdx.files.internal(nomeNivel));
        this.objeto1 = new LinkedList<BalloonsObjeto>();
        this.objeto3 = new LinkedList<BalloonsObjeto>();
        this.objeto2 = new LinkedList<BalloonsObjeto>();
        this.objeto4 = new LinkedList<BalloonsObjeto>();
        this.objeto5 = new LinkedList<BalloonsObjeto>();
        init();
    }

    public enum PIXEL_TIPO{
        PIPA(255, 0, 0),//objeto 1
        GALHO(100,0,0),//objeto 1
        POSTE(100,0,100),//objeto 1
        AVIAO(255, 255, 255),//objeto 2
        COBRA(255,255,0),//objeto 2
        PASSARO(0, 0, 255),//objeto 3
        DRONE(0,255,255),//objeto 3
        BOLHA(0,255,0),//objeto 4
        NUVEM(100,100,100),//objeto 5
        PREDIO(255,100,100),//objeto 5
        FLORESTA(0,100,0);//objeto 5

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
        Texture cobraTexure = new Texture("cobra.png");
        Texture galhoTexure = new Texture("galho.png");
        Texture posteTexure = new Texture("poste.png");
        Texture bolhaTexure = new Texture("bolha.png");
        Texture nuvemTexture = new Texture("nuvem.png");
        Texture predioTexture1 = new Texture("predio1.png");
        Texture predioTexture2 = new Texture("predio2.png");
        Texture florestaTexture1 = new Texture("floresta1.png");
        Texture florestaTexture2 = new Texture("floresta2.png");
        Texture florestaTexture3 = new Texture("floresta3.png");
        Array<TextureRegion> pipaMov = pegarImageFrames("pipa1.png");
        Array<TextureRegion> passaroVoo = pegarImageFrames("passaro.png");
        Array<TextureRegion> passaroPara = pegarImageFrames("passaro2.png");
        Array<TextureRegion> passaroAtaq = pegarImageFrames("passaro3.png");
        Array<TextureRegion> drone = pegarImageFrames("drone.png");
        float escala = Gdx.graphics.getWidth() / mapa.getWidth();
        for(int pixelY = 0; pixelY < mapa.getHeight(); pixelY++ ){
            for(int pixelX = 0; pixelX < mapa.getWidth(); pixelX++){
                currentPixel = mapa.getPixel(pixelX,pixelY);
                if(PIXEL_TIPO.PIPA.mesmaCor(currentPixel)) {
                    objeto1.add(new Pipa(pipaMov, pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.POSTE.mesmaCor(currentPixel)) {
                    objeto1.add(new Poste(posteTexure, pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.GALHO.mesmaCor(currentPixel)) {
                    objeto1.add(new Galho(pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.AVIAO.mesmaCor(currentPixel)){
                    objeto2.add(new Aviao(pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.COBRA.mesmaCor(currentPixel)){
                    objeto2.add(new Cobra(pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.PASSARO.mesmaCor(currentPixel)){
                    objeto3.add(new Passaro(passaroVoo, passaroPara, passaroAtaq, pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.DRONE.mesmaCor(currentPixel)){
                    objeto3.add(new Drone(pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.BOLHA.mesmaCor(currentPixel)){
                    objeto4.add(new Bolha(pixelX * escala, (mapa.getHeight() - pixelY) * escala));
                }else if(PIXEL_TIPO.NUVEM.mesmaCor(currentPixel)){
                    Random r = new Random();
                    int n = r.nextInt(20) + 180;
                    for(int i = 0; i < n; i++){
                        objeto5.add(new Nuvem());
                    }
                }else if(PIXEL_TIPO.PREDIO.mesmaCor(currentPixel)){
                    Random r = new Random();
                    for(float i = 0; i < 25;i++){
                        int n = r.nextInt(2);
                        if(n == 1)
                            objeto5.add(new Predio(predioTexture2, i));
                        else
                            objeto5.add(new Predio(predioTexture1, i));
                    }
                }else if(PIXEL_TIPO.FLORESTA.mesmaCor(currentPixel)){
                    for(float i = 0; i < 25; i++){
                        if(i < 20)
                            objeto5.add(new Floresta(BalloonsImagens.imagem.florestaTexture1,i));
                        else if(i < 24)
                            objeto5.add(new Floresta(BalloonsImagens.imagem.florestaTexture2,i));
                        else
                            objeto5.add(new Floresta(BalloonsImagens.imagem.florestaTexture3,i));
                    }
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

    public List<BalloonsObjeto> getObjeto1(){ return objeto1; }

    public List<BalloonsObjeto> getObjeto2(){ return objeto2; }

    public List<BalloonsObjeto> getObjeto3(){ return objeto3; }

    public List<BalloonsObjeto> getObjeto4(){ return objeto4; }

    public List<BalloonsObjeto> getObjeto5(){ return objeto5; }
}
