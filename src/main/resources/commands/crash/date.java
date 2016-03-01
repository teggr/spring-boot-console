import org.crsh.command.*;

import com.robintegg.OutputBean;
import org.springframework.beans.factory.BeanFactory;

import org.crsh.cli.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class date extends BaseCommand {
	@Usage("show the current time")
	@Command
	public Object main(@Usage("the time format") @Option(names = { "f", "format" }) String format) {

    	BeanFactory factory = (BeanFactory)context.getAttributes().get("spring.beanfactory");
    	
    	OutputBean bean = factory.getBean(OutputBean.class);
    	
    	bean.output();
    	
		if (format == null)
			format = "EEE MMM d HH:mm:ss z yyyy";
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	}
}
