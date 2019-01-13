package ai12.maven_ai12.main;

public enum SceneEnum {

    CONNECTION("views/ConnectionView.fxml"),

    MAIN("views/MainView.fxml"),

    LOBBYADMIN("views/LobbyAdminView.fxml"),

    LOBBYPLAYER("views/LobbyPlayerView.fxml");

    private String resource;

    SceneEnum(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
