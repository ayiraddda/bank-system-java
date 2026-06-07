package model;
public class Client {
    private int id;
    private String fullName;
    private String phone;
    public Client(int id, String fullName, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    @Override
    public String toString() {
        return "Client{id=" + id + ", fullName='" + fullName + "', phone='" + phone + "'}";
    }
}