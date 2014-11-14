package integrationOne;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Greeting {

    private final long id;
    private final String content;
    private Date time;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
    
    public String getTime(){
    	time = new Date();
    	return new SimpleDateFormat("hh:mm:ss").format(time);
    }
}
