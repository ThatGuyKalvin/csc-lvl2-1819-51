package uk.ac.qub.eeecs.game;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class LoadingAnimation extends Sprite {


    private static final float NUMBER_WIDTH = 50.0f;
    private static final float NUMBER_HEIGHT = 75.0f;
    private AnimationManager mAnimationManager;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    public LoadingAnimation(float startX, float startY, GameScreen gameScreen) {
        super(startX, startY, NUMBER_WIDTH, NUMBER_HEIGHT, null, gameScreen);

        mAnimationManager = new AnimationManager(this);
        mAnimationManager.addAnimation("txt/animation/LoadingAnimation.JSON");
        mAnimationManager.setCurrentAnimation("LoadingAnimation");

    }

    public void update(ElapsedTime elapsedTime){
        super.update(elapsedTime);
        mAnimationManager.play("LoadingAnimation", elapsedTime);
        mAnimationManager.update(elapsedTime);
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {
        // Get the animation manager to draw the current animation
        mAnimationManager.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
    }
}