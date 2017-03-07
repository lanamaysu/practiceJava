package pojo;

public class Customer {
	private String customerName;
	private String customerPhone;
	private String customerAddress;
	private String customerBirth;

	public String getName() {
		return customerName;
	}

	public void setName(String name) {
		this.customerName = name;
	}

	public String getPhone() {
		return customerPhone;
	}

	public void setPhone(String phoneNum) {
		this.customerPhone = phoneNum;
	}

	public String getAddress() {
		return customerAddress;
	}

	public void setAddress(String addr) {
		this.customerAddress = addr;
	}

	public String getBirth() {
		return customerBirth;
	}

	public void setBirth(String birthday) {
		this.customerBirth = birthday;
	}
}
