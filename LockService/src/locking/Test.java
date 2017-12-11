package locking;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;


@Path("/test")
public class Test {
	@POST
	@Consumes({"application/json"})
	@Path("/signIn")
	public String myfunc(String input){

		System.out.println(input);
		return "{\"message\":\"welcome Test\"}";

	}
}