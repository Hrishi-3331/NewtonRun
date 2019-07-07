package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hrishi3331games.devstudio3331.game.Controller;
import com.hrishi3331games.devstudio3331.game.MyCoolGame;

import java.util.ArrayList;
import java.util.Random;

import Scenes.PlayArena;

public class PlayScreen implements Screen {

    MyCoolGame game;
    Controller controller;
    private Viewport gameport;
    private OrthographicCamera gamecam;
    private PlayArena arena;
    private SpriteBatch batch;
    private Texture[] Newton;
    private int state = 0;
    private int Rstate = 0;
    private int pause = 0;
    private int speed = 5;
    private float manYpermanent;
    private float manY;
    private float manX;
    private float manX_max;
    private float manX_min;
    private Texture start;
    private ArrayList<Integer> blockY;
    private ArrayList<Integer> blockX;
    private ArrayList<Rectangle> blockRectangle;
    private ArrayList<Rectangle> appleRectangle;
    private ArrayList<Float> blockWidth;
    private Texture block;
    private Texture apple;
    private ArrayList<Integer> appleY;
    private ArrayList<Integer> appleX;
    private Random random;
    private int blockCount;
    private int appleCount;
    private Texture[] NewtonRest;
    private Texture[] NewtonJump;
    private int backRun;
    private float velocity;
    private float gravity;
    private boolean gravity_active;
    private float apple_width;
    private float apple_height;
    private Texture tunnel;
    private int tunnelCount;
    private ArrayList<Integer> tunnelX;
    private ArrayList<Rectangle> tunnelRectangle;
    private ArrayList<Float> TunnelWidth;
    private float tunnel_height;
    private Rectangle PlayerRectangle;


    public PlayScreen(MyCoolGame game) {
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new ScreenViewport(gamecam);
        arena = new PlayArena(game.batch);
        controller = new Controller(game.batch);
        batch = game.batch;

        // Running Frames

        Newton = new Texture[6];
        Newton[0] = new Texture("frame-1.png");
        Newton[1] = new Texture("frame-2.png");
        Newton[2] = new Texture("frame-3.png");
        Newton[3] = new Texture("frame-4.png");
        Newton[4] = new Texture("frame-5.png");
        Newton[5] = new Texture("frame-6.png");

        // Resting Frame

        NewtonRest = new Texture[2];
        NewtonRest[0] = new Texture("idle1.png");
        NewtonRest[1] = new Texture("idle2.png");

        // Jumping Frame

        NewtonJump = new Texture[2];
        NewtonJump[0] = new Texture("jump_up.png");
        NewtonJump[1] = new Texture("jump_fall.png");


        state = 0;
        pause = 0;
        backRun = 0;
        velocity = 0;
        gravity = 2f;
        gravity_active = false;

        random = new Random();
        manX_min = Gdx.graphics.getWidth()/3 - Newton[state].getWidth();
        manX_max =  Gdx.graphics.getWidth()/2 - Newton[1].getWidth()/2;
        manX = Gdx.graphics.getWidth()/3 - Newton[state].getWidth();
        manYpermanent = Gdx.graphics.getHeight()/3 - Newton[state].getHeight()/4 + 20;
        manY = manYpermanent;

        // Objects

        start = new Texture("start.png");
        block = new Texture("block.png");
        apple = new Texture("apple.png");
        tunnel = new Texture("tunnel.png");
        blockCount = 0;
        appleCount = 0;
        tunnelCount = 0;
        apple_width = apple.getWidth()/4;
        apple_height = apple.getHeight()/4;

        // Arraylists

        blockY = new ArrayList<Integer>();
        blockX = new ArrayList<Integer>();
        blockRectangle = new ArrayList<Rectangle>();
        blockWidth = new ArrayList<Float>();
        appleX = new ArrayList<Integer>();
        appleY = new ArrayList<Integer>();
        appleRectangle = new ArrayList<Rectangle>();
        tunnelRectangle = new ArrayList<Rectangle>();
        tunnelX = new ArrayList<Integer>();
        TunnelWidth = new ArrayList<Float>();
        tunnel_height = tunnel.getHeight()*5/3 - 25;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(start, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.batch.setProjectionMatrix(controller.stage.getCamera().combined);
        PlayerRectangle = new Rectangle(manX + 10, manY + 30, Newton[state].getWidth()/2 - 20, Newton[state].getHeight()/2 - 20);

        if (arena.isRight_pressed()){

            run();
            if (backRun > 0){
                backRun--;
            }
            else {
                if (blockCount < 150) {
                    blockCount++;

                } else {
                    blockCount = 0;
                    makeBlock();
                }

                if (appleCount < 100) {
                    appleCount++;

                } else {
                    appleCount = 0;
                    makeApple();
                }

                if (tunnelCount < 200) {
                    tunnelCount++;

                } else {
                    tunnelCount = 0;
                    if (random.nextBoolean()) {
                        makeTunnel();
                    }
                }
            }

            blockRectangle.clear();
            for (int i = 0; i < blockX.size(); i++) {
                float blockwidth = blockWidth.get(i);
                batch.draw(block, blockX.get(i), blockY.get(i), blockwidth , block.getHeight());
                blockRectangle.add(new Rectangle(blockX.get(i) + 10, blockY.get(i) + 10, blockwidth - 10 , block.getHeight() + 40));
                blockX.set(i, blockX.get(i) - 6);
            }

            appleRectangle.clear();
            for (int i = 0; i < blockX.size(); i++) {
                batch.draw(apple, appleX.get(i), appleY.get(i), apple_width , apple_height);
                appleRectangle.add(new Rectangle(appleX.get(i) + 10, appleY.get(i) + 10, apple_width - 10 , apple_height - 20));
                appleX.set(i, appleX.get(i) - 6);
            }

            tunnelRectangle.clear();
            for (int i = 0; i < tunnelX.size(); i++) {
                batch.draw(tunnel, tunnelX.get(i), 0, TunnelWidth.get(i) , tunnel_height);
                tunnelRectangle.add(new Rectangle(tunnelX.get(i) + 10,  10, TunnelWidth.get(i) - 10 , tunnel_height - 20));
                tunnelX.set(i, tunnelX.get(i) - 6);
            }
        }

        else if (arena.isLeft_pressed()){
           runBack();

            blockRectangle.clear();
            for (int i = 0; i < blockX.size(); i++) {
                float blockwidth = blockWidth.get(i);
                batch.draw(block, blockX.get(i), blockY.get(i), blockwidth , block.getHeight());
                blockRectangle.add(new Rectangle(blockX.get(i) + 10, blockY.get(i) + 10, blockwidth - 10 , block.getHeight() + 40));
                blockX.set(i, blockX.get(i) + 6);
            }

            appleRectangle.clear();
            for (int i = 0; i < blockX.size(); i++) {
                batch.draw(apple, appleX.get(i), appleY.get(i), apple_width , apple_height);
                appleRectangle.add(new Rectangle(appleX.get(i) + 10, appleY.get(i) + 10, apple_width - 10 , apple_height - 20));
                appleX.set(i, appleX.get(i) + 6);
            }


            tunnelRectangle.clear();
            for (int i = 0; i < tunnelX.size(); i++) {
                batch.draw(tunnel, tunnelX.get(i), 0, TunnelWidth.get(i) , tunnel_height);
                tunnelRectangle.add(new Rectangle(tunnelX.get(i) + 10,  10, TunnelWidth.get(i) - 10 , tunnel_height - 20));
                tunnelX.set(i, tunnelX.get(i) + 6);
            }
        }


        else if (arena.isUp_pressed()){
            if (!gravity_active) {
                jump();
            }

            batch.draw(Newton[state], manX, manY, Newton[state].getWidth()/2, Newton[state].getHeight()/2);

            blockRectangle.clear();
            for (int i = 0; i < blockX.size(); i++) {
                float blockwidth = blockWidth.get(i);
                batch.draw(block, blockX.get(i), blockY.get(i), blockwidth, block.getHeight());
                blockRectangle.add(new Rectangle(blockX.get(i) + 10, blockY.get(i) + 10, blockwidth - 10, block.getHeight() + 40));
            }

            appleRectangle.clear();
            for (int i = 0; i < blockX.size(); i++) {
                batch.draw(apple, appleX.get(i), appleY.get(i), apple_width , apple_height);
                appleRectangle.add(new Rectangle(appleX.get(i) + 10, appleY.get(i) + 10, apple_width - 10 , apple_height - 20));
            }

            tunnelRectangle.clear();
            for (int i = 0; i < tunnelX.size(); i++) {
                batch.draw(tunnel, tunnelX.get(i), 0, TunnelWidth.get(i) , tunnel_height);
                tunnelRectangle.add(new Rectangle(tunnelX.get(i) + 10,  10, TunnelWidth.get(i) - 10 , tunnel_height - 20));
            }
        }

        else {
            rest();
            blockRectangle.clear();
            for (int i = 0; i < blockX.size(); i++) {
                float blockwidth = blockWidth.get(i);
                batch.draw(block, blockX.get(i), blockY.get(i), blockwidth, block.getHeight());
                blockRectangle.add(new Rectangle(blockX.get(i) + 10, blockY.get(i) + 10, blockwidth - 10, block.getHeight() + 40));
            }

            appleRectangle.clear();
            for (int i = 0; i < blockX.size(); i++) {
                batch.draw(apple, appleX.get(i), appleY.get(i), apple_width , apple_height);
                appleRectangle.add(new Rectangle(appleX.get(i) + 10, appleY.get(i) + 10, apple_width - 10 , apple_height - 20));
            }

            tunnelRectangle.clear();
            for (int i = 0; i < tunnelX.size(); i++) {
                batch.draw(tunnel, tunnelX.get(i), 0, TunnelWidth.get(i) , tunnel_height);
                tunnelRectangle.add(new Rectangle(tunnelX.get(i) + 10,  10, TunnelWidth.get(i) - 10 , tunnel_height - 20));
            }
        }

        for (int i = 0; i < blockRectangle.size(); i++) {
            if (Intersector.overlaps(PlayerRectangle, blockRectangle.get(i))) {
                gravity_active = false;
            }
        }

        if (gravity_active) {
            manY = manY - velocity;
            velocity = velocity + gravity;
        }

        if (manY <= manYpermanent){
           manY = manYpermanent;
           gravity_active = false;
           velocity = 0;
        }

        batch.end();
        arena.stage.draw();
    }

    private void rest() {
        if (pause < speed){
            pause ++;
        }
        else {
            pause = 0;
            if (Rstate < 1){
                Rstate ++;
            }
            else {
                Rstate = 0;
            }
        }
        batch.draw(NewtonRest[Rstate], manX, manY, NewtonRest[Rstate].getWidth()/2, NewtonRest[Rstate].getHeight()/2);
    }

    private void makeBlock(){
        float posX = Gdx.graphics.getWidth();
        float posY = Gdx.graphics.getHeight()/4 + (Gdx.graphics.getHeight()/2 * random.nextFloat());
        blockX.add((int) posX);
        blockY.add((int) posY);
        float blockwidth = block.getWidth()/2 + (block.getWidth()/2 * random.nextFloat());
        blockWidth.add(blockwidth);
    }

    private void makeApple(){
        float posX = Gdx.graphics.getWidth();
        float posY = Gdx.graphics.getHeight()/4 + (Gdx.graphics.getHeight()/2 * random.nextFloat());
        appleX.add((int) posX);
        appleY.add((int) posY);
    }

    private void makeTunnel(){
        float posX = Gdx.graphics.getWidth();
        float width = tunnel.getWidth() + tunnel.getWidth()*random.nextFloat();
        tunnelX.add((int) posX);
        TunnelWidth.add(width);
    }

    private void run() {
        if (pause < speed){
            pause ++;
        }
        else {
            pause = 0;
            if (state < 5){
                state ++;
            }
            else {
                state = 0;
            }
        }
        if (manX < manX_max){
            manX += 5;
        }
        batch.draw(Newton[state], manX, manY, Newton[state].getWidth()/2, Newton[state].getHeight()/2);
    }

    private void runBack() {
        if (pause < speed){
            pause ++;
        }
        else {
            pause = 0;
            if (state > 0){
                state --;
            }
            else {
                state = 5;
            }
        }
        if (manX > manX_min){
            manX -=5;
        }
        backRun ++;
        batch.draw(Newton[state], manX, manY, Newton[state].getWidth()/2, Newton[state].getHeight()/2);
    }

    private void jump(){
        velocity = -40;
        gravity_active = true;
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
