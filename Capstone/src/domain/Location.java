package domain;

public class Location {
	
	String locationId;
	String street;
	String city;
	String country;
	String state;
	String zip;
        String userId;
        Double taxRate;
	
	public Location() {
		super();
	}
	
	public Location(String locationId, String userID, double taxrate, String street, String city, String country, String state, String zip) {
		super();
               
		this.locationId = locationId;
		this.street = street;
		this.city = city;
		this.country = country;
		this.state = state;
		this.zip = zip;
                this.userId=userID;
                this.taxRate=taxrate;
	}
        
    public double getTaxRate(){
        return taxRate;
    }
    
    public void setTaxRate(double newRate){
        this.taxRate=newRate;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getUserId() {
        return userId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Location{" + "locationId=" + locationId + ", street=" + street + ", city=" + city + ", country=" + country + ", state=" + state + ", zip=" + zip + ", userId=" + userId + ", taxRate=" + taxRate + '}';
    }	
}