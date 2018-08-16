package balloons.recursos;

import com.badlogic.gdx.graphics.Texture;

public class BalloonsImagens {
    public static final BalloonsImagens imagem = new BalloonsImagens();
    public Texture florestaTexture3;
    public Texture florestaTexture2;
    public Texture florestaTexture1;
    public Texture predioTexture2;
    public Texture predioTexture1;
    public Texture nuvemTexture;
    public Texture aviaoTexture;
    public Texture bolhaTexure;
    public Texture posteTexure;
    public Texture galhoTexure;
    public Texture cobraTexure;
    public Texture ballonsSalvatioLogo;
    public Texture balaoTexture;
    public Texture background;
    public Texture backgroundGeral;
    public Texture botao_jogar;
    public Texture botao_score;
    public Texture botao_proximo;
    public Texture back_entrada;
    public Texture botao_menu;
    public Texture botao_salvar;
    public Texture balloon_cursor;
    public Texture droneSheet;
    public Texture pipaSheet;
    public Texture paassAtsheet;
    public Texture passVooSheet;
    public Texture passParSheet;

    public void init(){
        this.ballonsSalvatioLogo = new Texture("BalloonsSalvation.png");
        this.cobraTexure = new Texture("cobra.png");
        this.galhoTexure = new Texture("galho.png");
        this.posteTexure = new Texture("poste.png");
        this.bolhaTexure = new Texture("bolha.png");
        this.aviaoTexture = new Texture("aviao_papel.png");
        this.nuvemTexture = new Texture("nuvem.png");
        this.predioTexture1 = new Texture("predio1.png");
        this.predioTexture2 = new Texture("predio2.png");
        this.florestaTexture1 = new Texture("floresta1.png");
        this.florestaTexture2 = new Texture("floresta2.png");
        this.florestaTexture3 = new Texture("floresta3.png");
        this.balaoTexture = new Texture("balao.png");
        this.background = new Texture("background.png");
        this.backgroundGeral = new Texture("backgroundGeral.png");
        this.botao_jogar = new Texture("botoes_balloon/botao_jogar.png");
        this.botao_score = new Texture("botoes_balloon/botao_score.png");
        this.botao_proximo = new Texture("botoes_balloon/botao_proximo.png");
        this.back_entrada = new Texture("botoes_balloon/back_entrada.png");
        this.botao_menu = new Texture("botoes_balloon/botao_menu.png");
        this.botao_salvar = new Texture("botoes_balloon/botao_salvar.png");
        this.balloon_cursor = new Texture("botoes_balloon/cursor.png");
        this.droneSheet = new Texture("drone.png");
        this.pipaSheet = new Texture("pipa1.png");
        this.passVooSheet = new Texture("passaro.png");
        this.passParSheet = new Texture("passaro2.png");
        this.paassAtsheet = new Texture("passaro3.png");
    }

    public void dispose(){
        this.cobraTexure.dispose();
        this.galhoTexure.dispose();
        this.posteTexure.dispose();
        this.bolhaTexure.dispose();
        this.aviaoTexture.dispose();
        this.nuvemTexture.dispose();
        this.predioTexture1.dispose();
        this.predioTexture2.dispose();
        this.florestaTexture1.dispose();
        this.florestaTexture2.dispose();
        this.florestaTexture3.dispose();
        this.balaoTexture.dispose();
        this.ballonsSalvatioLogo.dispose();
        this.background.dispose();
        this.backgroundGeral.dispose();
        this.botao_jogar.dispose();
        this.botao_score.dispose();
        this.botao_proximo.dispose();
        this.back_entrada.dispose();
        this.botao_menu.dispose();
        this.botao_salvar.dispose();
        this.balloon_cursor.dispose();
        this.droneSheet.dispose();
        this.pipaSheet.dispose();
        this.passVooSheet.dispose();
        this.paassAtsheet.dispose();
        this.passParSheet.dispose();
    }
}
