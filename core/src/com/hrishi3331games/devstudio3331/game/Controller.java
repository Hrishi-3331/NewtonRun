package com.hrishi3331games.devstudio3331.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Controller {

    private Viewport viewport;
    public Stage stage;
    OrthographicCamera camera;
    private boolean up, down, left, right;

    public Controller(SpriteBatch batch) {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport, batch);

        up = false;
        down = false;
        Table table = new Table();
        table.right();

        Image right_btn = new Image(new Texture("right.png"));
        Image left_btn = new Image(new Texture("right.png"));

        right_btn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                right = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                right = false;
            }
        });

        left_btn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                left = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                left = false;
            }
        });


        table.add(left_btn).size(left_btn.getImageWidth(), left_btn.getImageHeight());
        table.add();
        table.add(right_btn).size(right_btn.getImageWidth(), right_btn.getImageHeight());
        table.sizeBy(4f);

        stage.addActor(table);
    }

    public void Draw(){
        stage.draw();
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }
}
