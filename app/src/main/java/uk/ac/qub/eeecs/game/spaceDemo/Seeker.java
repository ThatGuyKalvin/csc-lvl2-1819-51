package uk.ac.qub.eeecs.game.spaceDemo;

import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.particle.Emitter;
import uk.ac.qub.eeecs.gage.engine.particle.ParticleSystemManager;
import uk.ac.qub.eeecs.gage.util.MathsHelper;
import uk.ac.qub.eeecs.gage.util.SteeringBehaviours;
import uk.ac.qub.eeecs.gage.util.Vector2;
import java.util.Random;

/**
 * AI controlled spaceship that will seek towards the player.
 *
 * Note: See the course documentation for extension/refactoring stories
 * for this class.
 *
 * @version 1.0
 */
public class Seeker extends SpaceEntity {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Default size for the Seeker this test
     */
    private static final float DEFAULT_RADIUS = 20;

    /**
     * Distance at which the spaceship should avoid other game objects
     */
    private float mSeparateThreshold = 75.0f;

    /**
     * Accumulators used to build up the net steering outcome
     */
    private Vector2 mAccAccumulator = new Vector2();
    private Vector2 mAccComponent = new Vector2();

    /**
     * Particle emitter providing a thrust trail for this spaceship
     */
    private Emitter movementEmitter;

    /**
     * Offset/location variables for the movement emitter so it appears to
     * exit from the back of the spaceship.
     */
    private Vector2 movementEmitterOffset;
    private Vector2 movementEmitterLocation;

    private static int seekerSize = seekerSizeGenerator();

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create an AI controlled seeker spaceship
     *
     * @param startX        x location of the AI spaceship
     * @param startY        y location of the AI spaceship
     * @param gameScreen    Gamescreen to which AI belongs
     */
    public Seeker(float startX, float startY, SpaceshipDemoScreen gameScreen) {
        super(startX, startY, seekerSizeGenerator(), seekerSize, null, gameScreen);

        // Define movement variables for the seeker
        maxAcceleration = 30.0f;
        maxVelocity = 50.0f;
        maxAngularVelocity = 150.0f;
        maxAngularAcceleration = 300.0f;

        mRadius = DEFAULT_RADIUS;
        mMass = 10.0f;

        //selects a random seeker image
        String seekerImage = randomSeekerImage();

        // Define the appearance of the seeker
        mBitmap = gameScreen.getGame().getAssetManager().getBitmap(seekerImage);

        // Create an offset for the movement emitter based on the size of the spaceship
        movementEmitterOffset = new Vector2(-DEFAULT_RADIUS, 0.0f);
        movementEmitterLocation = new Vector2(position);
        movementEmitterLocation.add(movementEmitterOffset);

        // Create and add a particle effect for the movement of the ship
        Random random = new Random();
        ParticleSystemManager particleSystemManager =
                ((SpaceshipDemoScreen) mGameScreen).getParticleSystemManager();
        movementEmitter = new Emitter(
                particleSystemManager, random.nextBoolean() ? "txt/particle/ThrusterEmitter.JSON" : "txt/particle/ThrusterEmitter2.JSON",
                movementEmitterLocation);
        particleSystemManager.addEmitter(movementEmitter);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    /**
     * generates a random size (using the seeker size method) and returns it to the super
     * sets the integer 'seekerSize' to the same value as to maintain the aspect ratio.
    */

    public static int seekerSizeGenerator(){
        seekerSize = randomSeekerSize(15,45);
        return seekerSize;
    }

    /**
     * @param min   The minimum size that the seekers should be
     * @param max   The maximum size that the seekers should be
     * @return      Just returns a random integer between these values
     */

    public static int randomSeekerSize(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    //uses Random() to select a random image for the seeker.
    public String randomSeekerImage(){
        Random random = new Random();
        int randomSeekerNum = random.nextInt(6);
        switch (randomSeekerNum){
            case 1 : return "Spaceship2";
            case 2: return "Spaceship3";
            case 3: return "Spaceship4";
            case 4: return "Spaceship5";
            case 5: return "Spaceship6";
            case 6: return "Spaceship7";
            default: return "Spaceship2";
        }
    }



    /**
     * Update the AI Spaceship
     *
     * @param elapsedTime Elapsed time information
     */
    @Override
    public void update(ElapsedTime elapsedTime) {

        // Seek towards the player
        SteeringBehaviours.seek(this,
                ((SpaceshipDemoScreen) mGameScreen).getPlayerSpaceship().position,
                acceleration);

        // Try to avoid a collision with the player ship
        SteeringBehaviours.separate(this,
                ((SpaceshipDemoScreen) mGameScreen).getPlayerSpaceship(),
                mSeparateThreshold, 1.0f, mAccComponent);
        mAccAccumulator.set(mAccComponent);

        // Try to avoid a collision with the other space entities
        SteeringBehaviours.separate(this,
                ((SpaceshipDemoScreen) mGameScreen).getSpaceEntities(),
                mSeparateThreshold, 1.0f, mAccComponent);
        mAccAccumulator.add(mAccComponent);

        // If we are trying to avoid a collision then combine
        // it with the seek behaviour, placing more emphasis on
        // avoiding a collision.
        if (!mAccAccumulator.isZero()) {
            acceleration.x = 0.1f * acceleration.x + 1.5f * mAccAccumulator.x;
            acceleration.y = 0.1f * acceleration.y + 1.5f * mAccAccumulator.y;
        }

        // Make sure we point in the direction of travel.
        angularAcceleration = SteeringBehaviours.alignWithMovement(this);

        // Call the sprite's superclass to apply the determined accelerations
        super.update(elapsedTime);

        // Update the particle emitter associated with this ship to rhe new position,
        // calculating an offset so the particles emerge from the rear of the ship
        MathsHelper.rotateOffsetAboutCentre(
                position, movementEmitterOffset, orientation, movementEmitterLocation);
        movementEmitter.setPosition(movementEmitterLocation.x, movementEmitterLocation.y);
    }
}
