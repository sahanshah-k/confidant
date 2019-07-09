package com.example.confidant.Domain;

public class Secrete {
    int id;
    String secreteName;
    String secreteKey;
    String description;

    public Secrete() {
    }

    public Secrete(int id, String secreteName, String secreteKey, String description) {
        this.id = id;
        this.secreteName = secreteName;
        this.secreteKey = secreteKey;
        this.description = description;
    }

    public Secrete(String secreteName, String secreteKey, String description) {
        this.secreteName = secreteName;
        this.secreteKey = secreteKey;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecreteName() {
        return secreteName;
    }

    public void setSecreteName(String secreteName) {
        this.secreteName = secreteName;
    }

    public String getSecreteKey() {
        return secreteKey;
    }

    public void setSecreteKey(String secreteKey) {
        this.secreteKey = secreteKey;
    }
}
