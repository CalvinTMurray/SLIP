package dataAccessLayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class TestControllerSingleton {
	
	@Autowired
	private GameDAO dao;
	
	public GameDAO getDao(){
		return dao;
	}

}
