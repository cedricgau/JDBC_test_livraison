package JDBC;

public class Client {
	
	private int noclient;
	private String nomClient;
	private String notelephone;
	
	public Client(int noclient , String nomClient, String notelephone) {
		setNoclient(noclient);
		setNomClient(nomClient);
		setNotelephone(notelephone);
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public String getNotelephone() {
		return notelephone;
	}

	public void setNotelephone(String notelephone) {
		this.notelephone = notelephone;
	}

	public String getNomClient() {
		return nomClient;
	}

	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}

	public int getNoclient() {
		return noclient;
	}

	public void setNoclient(int noclient) {
		this.noclient = noclient;
	}

}
