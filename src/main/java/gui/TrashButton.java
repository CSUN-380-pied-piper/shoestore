package gui;

import javafx.scene.control.Button;

public class TrashButton extends Button {

    public TrashButton() {
        super();
        this.setStyle("-fx-background-color: red;" +
                " -fx-shape: \"M4,5 L7,5 L7,4 C7,2.8954305 7.8954305,2 9,2 L15,2 C16" +
                ".1045695,2 17,2" +
                ".8954305" +
                " 17,4" +
                " L17,5 L20,5 C20.5522847,5 21,5.44771525 21,6 C21,6.55228475 20.5522847,7 20,7 L19,7 L19,20 C19,21" +
                ".1045695 18.1045695,22 17,22 L7,22 C5.8954305,22 5,21.1045695 5,20 L5,7 L4,7 C3.44771525,7 3,6" +
                ".55228475 3,6 C3,5.44771525 3.44771525,5 4,5 Z M7,7 L7,20 L17,20 L17,7 L7,7 Z M9,5 L15,5 L15,4 L9,4 " +
                "L9,5 Z M9,9 L11,9 L11,18 L9,18 L9,9 Z M13,9 L15,9 L15,18 L13,18 L13,9 Z\" ");
    }
}