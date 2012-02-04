package de.main;

import java.util.Date;
import java.util.GregorianCalendar;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View;

public class MainActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;

	private Camera mCamera;
	private Texture ParkscheibeFrontTexture;
	private TextureRegion ParkscheibeFrontFaceTextureRegion;
	private Texture ParkscheibeScheibeTexture;
	private TextureRegion ParkscheibeScheibeFaceTextureRegion;
	
	Sprite face;
	Sprite face2;
	
	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
	}

	@Override
	public void onLoadResources() {
	
		this.ParkscheibeScheibeTexture = new Texture(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.ParkscheibeScheibeFaceTextureRegion = TextureRegionFactory.createFromAsset(this.ParkscheibeScheibeTexture, this, "gfx/Parkscheibe_Scheibe_1024.png", 0, 0);
		
		this.ParkscheibeFrontTexture = new Texture(1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.ParkscheibeFrontFaceTextureRegion = TextureRegionFactory.createFromAsset(this.ParkscheibeFrontTexture, this, "gfx/Parkscheibe_Front_1024.png", 0, 0);

		this.mEngine.getTextureManager().loadTextures(this.ParkscheibeScheibeTexture, this.ParkscheibeFrontTexture);
	}

	@Override
	public Scene onLoadScene() {
		
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene(1);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
		
		face = new Sprite(-78, -97, this.ParkscheibeScheibeFaceTextureRegion);
	
		face2 = new Sprite(0, 0, this.ParkscheibeFrontFaceTextureRegion);
		scene.getLastChild().attachChild(face);
		scene.getLastChild().attachChild(face2);
		
		face.setRotationCenter(319, 319);
		
		// Setzt automatisch die aktuelle Zeit als Rotation ein
		Date date = new Date(System.currentTimeMillis());
		float rh = (date.getHours()) * 30;
		float rm = date.getMinutes() / 2;
		float r = (rh + rm);
		face.setRotation(r);
		
		scene.setOnSceneTouchListener(new IOnSceneTouchListener() {
			
			float rotationBuffer = 0;
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
				if ( pSceneTouchEvent.getX() > rotationBuffer )
					face.setRotation(face.getRotation() - 2 );
				else 
					face.setRotation(face.getRotation() + 2 );
				
				rotationBuffer = pSceneTouchEvent.getX();
				return true;
			}
			
		});
		
		return scene;

	}

	@Override
	public void onLoadComplete() {

	}

}