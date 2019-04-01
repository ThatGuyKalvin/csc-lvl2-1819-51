package uk.ac.qub.eeecs.game.RiskGame;
// just a sprite that can be used to show more clearly if the attackers or the defenders win.

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.animation.AnimationManager;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.LayerViewport;
import uk.ac.qub.eeecs.gage.world.ScreenViewport;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class WinnerAnimation extends Sprite {


    private static final float NUMBER_WIDTH = 100.0f;
    private static final float NUMBER_HEIGHT = 150.0f;
    private AnimationManager mAnimationManager;
    private boolean attwin;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    public WinnerAnimation(float startX, float startY, GameScreen gameScreen, boolean winner) {
        super(startX, startY, NUMBER_WIDTH, NUMBER_HEIGHT, null, gameScreen);
        attwin = winner;
        mAnimationManager = new AnimationManager(this);
        mAnimationManager.addAnimation("txt/animation/DefendersWin.JSON");
        mAnimationManager.addAnimation("txt/animation/AttackersWin.JSON");
        mAnimationManager.setCurrentAnimation("DefendersWinAnimation");

    }

    public void update(ElapsedTime elapsedTime){
        super.update(elapsedTime);
        if(attwin){
            mAnimationManager.play("AttackersWinAnimation", elapsedTime);
            mAnimationManager.update(elapsedTime);
        }else {
            mAnimationManager.play("DefendersWinAnimation", elapsedTime);
            mAnimationManager.update(elapsedTime);
        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D,
                     LayerViewport layerViewport, ScreenViewport screenViewport) {
        // Get the animation manager to draw the current animation
        mAnimationManager.draw(elapsedTime, graphics2D, layerViewport, screenViewport);
    }
}