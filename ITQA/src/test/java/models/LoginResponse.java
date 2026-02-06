package models;

public class LoginResponse {
    private String token;
    private String tokenType;

    public LoginResponse() {
    }

    public LoginResponse(String token, String tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
