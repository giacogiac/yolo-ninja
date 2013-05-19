package security.module;

public class BarkerPrincipal implements java.security.Principal, java.io.Serializable {
	private static final long serialVersionUID = -3116013246267886494L;
	
	private String name;
	
	public BarkerPrincipal(String name){
		this.name=name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (this == o)
			return true;

		if (!(o instanceof BarkerPrincipal))
			return false;
		BarkerPrincipal that = (BarkerPrincipal)o;

		if (this.getName().equals(that.getName()))
			return true;
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
