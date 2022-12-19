package com.example.androidproject08;

public class UsedVoucher {
    private String id;
    private String user;
    private String idVoucher;

    public UsedVoucher() {
    }

    public UsedVoucher(String user, String idVoucher) {
        this.user = user;
        this.idVoucher = idVoucher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(String idVoucher) {
        this.idVoucher = idVoucher;
    }
}
