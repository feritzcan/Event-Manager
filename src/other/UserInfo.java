package other;

public class UserInfo {
	
	public String image;
	public String name;
	public String info;
	public String password;
	public String freeTimes;
	public UserInfo(String name,String image,String info,String password,String free)
	{
		this.password=password;
		this.info=info;
		this.image=image;
		this.name=name;
		this.freeTimes=free;
	}

}
