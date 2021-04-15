package utils;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TransitionUtil {
	public static FadeTransition makeFadingNode(Node node, double from, double to) {
		FadeTransition fade = new FadeTransition(Duration.seconds(1.0), node);

		fade.setFromValue(from);
		fade.setToValue(to);

		return fade;
	}
}
