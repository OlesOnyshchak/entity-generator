package com.softserve.entity.generator.salesforce;

public class Credentials
{
    private final String username;
    private final String password;
    private final String token;

    public Credentials(String username, String password, String token)
    {
        this.username = username;
        this.password = password;
        this.token = token;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getToken()
    {
        return token;
    }
}
