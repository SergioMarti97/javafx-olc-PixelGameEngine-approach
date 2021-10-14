package olcPGEApproach;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.HashMap;

/**
 * This class manages the user
 * inputs about the keyboard, mouse buttons clicks
 * and its relative position on screen, and the
 * mouse wheel
 */
public class Input {

    private final Node node;

    private final HashMap<KeyCode, Boolean> mapKeysState;

    private final HashMap<KeyCode, Boolean> mapKeysStateLast;


    private final HashMap<KeyCode, Boolean> mapKeysPress;

    private final HashMap<KeyCode, SimpleIntegerProperty> mapKeysCount;


    private final HashMap<MouseButton, Boolean> mapButtons;

    private final HashMap<MouseButton, Boolean> mapButtonLast;

    private double mouseX;

    private double mouseY;

    private int scroll;

    public Input(Node node) {
        this.node = node;

        mouseX = 0.0;
        mouseY = 0.0;
        scroll = 0;

        mapKeysState = new HashMap<>();
        for ( KeyCode keyCode : KeyCode.values() ) {
            mapKeysState.put(keyCode, Boolean.FALSE);
        }
        mapKeysStateLast = new HashMap<>();
        for ( KeyCode keyCode : KeyCode.values() ) {
            mapKeysStateLast.put(keyCode, Boolean.FALSE);
        }

        mapButtons = new HashMap<>();
        for ( MouseButton btn : MouseButton.values() ) {
            mapButtons.put(btn, Boolean.FALSE);
        }
        mapButtonLast = new HashMap<>();
        for ( MouseButton btn : MouseButton.values() ) {
            mapButtonLast.put(btn, Boolean.FALSE);
        }

        mapKeysPress = new HashMap<>();
        for ( KeyCode keyCode : KeyCode.values() ) {
            mapKeysPress.put(keyCode, Boolean.FALSE);
        }

        mapKeysCount = new HashMap<>();
        for ( KeyCode keyCode : KeyCode.values() ) {
            mapKeysCount.put(keyCode, new SimpleIntegerProperty(0));
        }

        setNodeClickEvents();
        setNodeKeyEvents();
        node.setOnScroll(event -> scroll += (int) event.getDeltaY() / 40);
    }

    private void setNodeKeyEvents() {
        node.setOnKeyPressed(event -> {
            System.out.println("Key pressed: " + event.getCode());
            mapKeysState.replace(event.getCode(), Boolean.TRUE);
            mapKeysPress.replace(event.getCode(), Boolean.TRUE);

            mapKeysCount.get(event.getCode()).set(mapKeysCount.get(event.getCode()).get() + 1);
        });
        node.setOnKeyReleased(event -> {
            System.out.println("Key released: " + event.getCode());
            mapKeysState.replace(event.getCode(), Boolean.FALSE);
            mapKeysPress.replace(event.getCode(), Boolean.FALSE);

            mapKeysCount.get(event.getCode()).set(0);
        });

        /*node.setOnKeyPressed(keyEvent ->
                mapKeysCount.get(keyEvent.getCode()).set(mapKeysCount.get(keyEvent.getCode()).get() + 1));

        node.setOnKeyReleased(keyEvent ->
                mapKeysCount.get(keyEvent.getCode()).set(0));*/
    }

    private void setNodeClickEvents() {
        node.setOnMousePressed(event -> {
            mapButtons.replace(event.getButton(), Boolean.TRUE);
        });
        node.setOnMouseReleased(event -> {
            mapButtons.replace(event.getButton(), Boolean.FALSE);
        });
        node.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });
    }

    public boolean isKeyDown2(KeyCode keyCode) {
        return mapKeysPress.get(keyCode);
    }

    public void update() {
        mapKeysStateLast.clear();
        mapKeysStateLast.putAll(mapKeysState);

        mapButtonLast.clear();
        mapButtonLast.putAll(mapButtons);
    }

    public boolean isKey(KeyCode keyCode) {
        return mapKeysState.get(keyCode);
    }

    public boolean isKeyLast(KeyCode keyCode) {
        return mapKeysStateLast.get(keyCode);
    }

    public boolean isKeyUp(KeyCode keyCode) {
        boolean isPressed = mapKeysState.get(keyCode);
        boolean wasPressed = mapKeysStateLast.get(keyCode);
        return !isPressed && wasPressed;
    }

    public boolean isKeyDown(KeyCode keyCode) {
        /*boolean isPressed = mapKeysState.get(keyCode);
        boolean wasPressed = mapKeysStateLast.get(keyCode);
        return isPressed && !wasPressed;*/
        return mapKeysCount.get(keyCode).isEqualTo(1).get();
    }

    public boolean isKeyHeld(KeyCode keyCode) {
        boolean isPressed = mapKeysState.get(keyCode);
        boolean wasPressed = mapKeysStateLast.get(keyCode);
        return isPressed && wasPressed;
    }

    public boolean isButton(MouseButton button) {
        return mapButtons.get(button);
    }

    public boolean isButtonUp(MouseButton button) {
        return !mapButtons.get(button) && mapButtonLast.get(button);
    }

    public boolean isButtonDown(MouseButton button) {
        return mapButtons.get(button) && !mapButtonLast.get(button);
    }

    public boolean isButtonHeld(MouseButton button) {
        return mapButtons.get(button) && mapButtonLast.get(button);
    }

    public Node getNode() {
        return node;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }

}
