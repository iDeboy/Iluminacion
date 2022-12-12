package test;

import static com.jogamp.opengl.fixedfunc.GLLightingFunc.*;
import java.awt.Color;
import java.util.Arrays;
import models.GlLight;
import models.SphereModel;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class TestLight {

	public static void main(String[] args) {

		SphereModel sp = new SphereModel(0, 0, 0, Color.magenta, Color.yellow, Color.yellow, 10, Color.pink);
		SphereModel sp2 = new SphereModel(0, 0, 0, Color.magenta, Color.yellow, Color.yellow, 10, Color.pink);

		sp.setLight(GL_LIGHT0);
		sp2.setLight(GL_LIGHT1);
		sp.setLight(0);

		System.out.println(sp.isLight());
		System.out.println(sp2.isLight());

		System.out.println(Arrays.toString(GlLight.values()));

	}

}
