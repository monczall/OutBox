package main.java.features;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Animations {

    /**
     * <p>
     * Function that moves two AnchorPanes by value on x-axis with
     * (if value is negative then it moves to the left, otherwise to the right)
     * Link to a method 'moveByX'" {@link #moveByX(Node, double, double)}
     * Function takes four arguments: two AnchorPanes; first pane is actual pane
     * that user can see, the second one is pane that user is going to see after animation is completed,
     * the next argument is value of how much pixels will panes move before
     * their visibility is set to false, next argument is duration of animation (in seconds).
     * </p>
     * @param actualPane pane which is actually showed
     * @param nextPane pane which will be moved
     * @param value number of pixels
     * @param duration seconds of animation
     */
    public static void changePane(AnchorPane actualPane, AnchorPane nextPane, double value, double duration){

        actualPane.setDisable(true);    //Panes are disabled for time when animation is played (it stops user for clicking another button and starts animation over again
        nextPane.setDisable(true);      // which leads to unintended problems

        FadeTransition fadeActual = new FadeTransition(Duration.seconds(duration), actualPane);
        fadeActual.setFromValue(1);
        fadeActual.setToValue(0);
        fadeActual.play();

        moveByX(actualPane, value, duration);

        fadeActual.setOnFinished(event -> {
            actualPane.setVisible(false);
        });

        nextPane.setVisible(true);

        FadeTransition fadeNext = new FadeTransition(Duration.seconds(duration), nextPane);
        fadeNext.setFromValue(0);
        fadeNext.setToValue(1);
        fadeNext.play();

        moveByX(nextPane, value, duration);

        fadeNext.setOnFinished(event -> {
            actualPane.setDisable(false);
            nextPane.setDisable(false);
        });
    }

    /**
     * <p>
     * Function that moves two AnchorPanes by value on y-axis
     * (if value is negative then it moves up , otherwise down)
     * Link to a method 'moveByX'" {@link #moveByY(Node, double, double)}
     * Function takes six arguments: two AnchorPanes; first pane is actual pane that user can see, the second one is pane that user is going to see after animation is completed,
     * the next argument is value of how much pixels will panes move before their visibility is set to false, next argument is duration of animation (in seconds). The last two
     * arguments are ToggleButtons that are being disabled to not let te user click them multiple times
     * </p>
     * @param actualPane pane which is actually showed
     * @param nextPane pane which will be moved
     * @param value number of pixels
     * @param duration seconds of animation
     * @param actualToggle button that caused the animation
     * @param nextToggle next button
     */
    public static void changePane(AnchorPane actualPane, AnchorPane nextPane, double value, double duration, ToggleButton actualToggle, ToggleButton nextToggle){

        actualToggle.setDisable(true);
        nextToggle.setDisable(true);
        actualToggle.setOpacity(1);
        nextToggle.setOpacity(1);

        FadeTransition fadeActual = new FadeTransition(Duration.seconds(duration), actualPane);
        fadeActual.setFromValue(1);
        fadeActual.setToValue(0);
        fadeActual.play();

        moveByY(actualPane, value, duration);

        fadeActual.setOnFinished(event -> {
            actualPane.setVisible(false);
        });

        nextPane.setVisible(true);

        FadeTransition fadeNext = new FadeTransition(Duration.seconds(duration), nextPane);
        fadeNext.setFromValue(0);
        fadeNext.setToValue(1);
        fadeNext.play();

        moveByY(nextPane, value, duration);

        fadeNext.setOnFinished(event -> {
            nextToggle.setDisable(false);
        });
    }

    /**
     * <p>
     *  Function that moves an item by value on x-axis (if value is negative then it moves to the left, otherwise to the right)
     *  Function takes node (e.g AnchorPane, Circle), value (number of pixels) and duration (seconds - how long animation is going to last) arguments
     * </p>
     * @param item node that will be moved
     * @param value number of pixels
     * @param duration seconds of animation
     */
    public static void moveByX(Node item, double value, double duration) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), item);
        translateTransition.setByX(value);
        translateTransition.play();
    }

    /**
     * <p>
     * Function that moves an item by value on y-axis (if value is negative then it moves up , otherwise down)
     * Function takes node (e.g AnchorPane, Circle), value (number of pixels) and duration (seconds - how long animation is going to last) arguments
     * </p>
     * @param item node that will be moved
     * @param value number of pixels
     * @param duration seconds of animation
     */
    public static void moveByY(Node item, double value, double duration) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), item);
        translateTransition.setByY(value);
        translateTransition.play();
    }

    /**
     * <p>
     * Method moves a Pane by given value, durationOfMove is a number of seconds
     * After animation is finished there is pause (durationOfPause)
     * and after pause, animation is played once again but with opposite Y-axis value.
     * It blocks button (causeOfAnim) so that prevent spamming button and
     * AnchorPane (parent) is used to create alert inside this AnchorPane.
     * </p>
     * @param item pane that will show up
     * @param value value of pixels in Y axis
     * @param durationOfMove time of move (in seconds)
     * @param durationOfPause time of pause after move is completed (in seconds)
     * @param causeOfAnim node that caused an animation
     * @param parent AnchorPane in which alert will be created
     */
    public static void alertAnim(Pane item, double value, double durationOfMove, double durationOfPause, Node causeOfAnim, AnchorPane parent){
        causeOfAnim.setDisable(true);
        causeOfAnim.setOpacity(1);
        TranslateTransition translateStart = new TranslateTransition(Duration.seconds(durationOfMove), item);
        translateStart.setByY(value);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(durationOfPause));

        TranslateTransition translateBack = new TranslateTransition(Duration.seconds(durationOfMove),item);
        translateBack.setByY(-value);

        SequentialTransition sequentialTransition = new SequentialTransition(translateStart,pauseTransition,translateBack);
        sequentialTransition.play();

        sequentialTransition.setOnFinished(event -> {
            parent.getChildren().remove(item);
            causeOfAnim.setDisable(false);
        });
    }

    /**
     * <p>
     *  Method that fade away an item.
     *  Method takes five arguments - node (e.g button), duration of fading in seconds
     *  valueFrom - starting opacity and valueTo - opacity at the finish of animation.
     *  visibility is set to either true or false depending on user input.
     * </p>
     * @param item that will be
     * @param duration seconds of animation
     * @param valueFrom starting opacity
     * @param valueTo opacity at the finish of animation
     * @param visibility boolean value that set visibility of button at the end of animation
     */
    public static void fadeAway(Node item, double duration,int valueFrom, int valueTo, boolean visibility){
        item.setDisable(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration),item);
        fadeTransition.setFromValue(valueFrom);
        fadeTransition.setToValue(valueTo);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {
            item.setVisible(visibility);
            item.setDisable(false);
        });
    }


}
