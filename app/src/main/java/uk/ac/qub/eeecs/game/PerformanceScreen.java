package uk.ac.qub.eeecs.game;

import uk.ac.qub.eeecs.gage.Game;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.gage.world.GameScreen;

public class PerformanceScreen extends GameScreen {

    /**
     * Create a new game screen associated with the specified game instance
     *
     * @param name
     * @param game Game instance to which the game screen belongs
     */

    public PerformanceScreen(String name, Game game) {
        super(name, game);
    }

    @Override
    public void update(ElapsedTime elapsedTime) {

    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {

    }
}
