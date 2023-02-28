package me.shelly.projectshelly;

public class ReadWriteUserDetails {
    private String userName;
    private String birthday;
    private String favColor;

    public ReadWriteUserDetails(){};

    public ReadWriteUserDetails(String userName, String birthday, String favColor) {
        this.userName = userName;
        this.birthday = birthday;
        this.favColor = favColor;
    }

    public String getFavColor() {
        return favColor;
    }

    public void setFavColor(String favColor) {
        this.favColor = favColor;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
