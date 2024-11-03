package com.myvg.myvg.DTO;

//Classe da usare per il trasferimento di dati tra il client e il server
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private int avatarID;

    public UserDTO() {}

    public UserDTO(String Id, String username, String email, int avatarID) {
        this.id = Id;
        this.username = username;
        this.email = email;
        this.avatarID = avatarID;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAvatarID() { return avatarID; }
    public void setAvatarID(int avatarID) { this.avatarID = avatarID; }

}
