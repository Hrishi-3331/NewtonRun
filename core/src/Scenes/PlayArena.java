package Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayArena {

    public Stage stage;
    private Viewport viewport;
    private Integer SCORE;
    private Integer LEVEL;

    Label SCORE_LABEL;
    Label GAME_LABEL;
    Label DEVELOPER_LABEL;

    Image forward, backward, upward, downward, attack;
    private boolean right_pressed;
    private boolean left_pressed;
    private boolean up_pressed;

    public PlayArena(SpriteBatch batch) {

        SCORE = 0;
        LEVEL = 1;
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        SCORE_LABEL = new Label(String.format("%06d", SCORE), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        SCORE_LABEL.setFontScale(4f);
        GAME_LABEL = new Label("NEWTON RUN",  new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        GAME_LABEL.setFontScale(4f);
        DEVELOPER_LABEL = new Label(String.format("%02d", LEVEL),  new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        DEVELOPER_LABEL.setFontScale(4f);

        Texture right = new Texture("right.png");
        Texture left = new Texture("left.png");
        Texture top = new Texture("top.png");
        Texture bottom = new Texture("bottom.png");
        Texture center = new Texture("center.png");

        forward = new Image(right);
        forward.setWidth(right.getWidth()/4);
        forward.setHeight(right.getHeight()/4);
        backward = new Image(left);
        backward.setWidth(right.getWidth()/4);
        backward.setHeight(right.getHeight()/4);
        upward = new Image(top);
        upward.setWidth(right.getWidth()/4);
        upward.setHeight(right.getHeight()/4);
        downward = new Image(bottom);
        downward.setWidth(right.getWidth()/4);
        downward.setHeight(right.getHeight()/4);
        attack = new Image(center);
        attack.setWidth(right.getWidth()/4);
        attack.setHeight(right.getHeight()/4);


        Table controller = new Table();
        controller.setWidth(Gdx.graphics.getWidth());
        controller.setHeight(Gdx.graphics.getHeight()/3 + 80);
        controller.padLeft(Gdx.graphics.getWidth()*3/4 - 35);
        controller.padRight(35);
        controller.padBottom(35);

        controller.row().pad(5);
        controller.add();
        controller.add(upward);
        controller.add();
        controller.row().pad(5);
        controller.add(backward);
        controller.add();
        controller.add(forward);
        controller.row().pad(5);
        controller.add();
        controller.add(downward);
        controller.add();


        Label tag1 = new Label("Level", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tag1.setFontScale(3.5f);
        table.add(tag1).expandX().padTop(20).padBottom(3);
        table.add().expandX();
        Label tag2 = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tag2.setFontScale(3.5f);
        table.add(tag2).expandX().padTop(20).padBottom(3);
        table.row();
        table.add(DEVELOPER_LABEL).expandX();
        table.add(GAME_LABEL).expandX().padLeft(90);
        table.add(SCORE_LABEL).expandX();
        table.row();

        Gdx.input.setInputProcessor(stage);

        forward.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                right_pressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                right_pressed = false;
            }
        });

        backward.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                left_pressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                left_pressed = false;
            }
        });

        upward.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                up_pressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                up_pressed = false;
            }
        });

        stage.addActor(controller);
        stage.addActor(table);
    }

    public boolean isRight_pressed() {
        return right_pressed;
    }

    public boolean isLeft_pressed() {
        return left_pressed;
    }

    public boolean isUp_pressed() {
        return up_pressed;
    }
}
