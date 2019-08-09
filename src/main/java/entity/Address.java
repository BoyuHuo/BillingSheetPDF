package entity;

public class Address {
    private String name;
    private String street;
    private String city;
    private String postCode;
    private String country;


    public Address(){}
    public Address(String name, String street, String city, String postCode, String country) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
