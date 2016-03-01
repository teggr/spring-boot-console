import org.crsh.cli.Command;
import org.crsh.cli.Usage;
import org.crsh.cli.Option;

import com.robintegg.OutputBean;
import org.springframework.beans.factory.BeanFactory;

class run {

	@Usage("the target environment")
	@Option(names=["e","environment"])
	private String environment;

  	@Usage("run available tests")
  	@Command
  	Object main(
    	@Usage("the list of tests to run")
    	@Argument List<String> tests
    	) {
    	
    	BeanFactory factory = context.attributes['spring.beanfactory']
    	
    	OutputBean bean = factory.getBean(OutputBean.class)
    	
    	bean.output();
    	
    	return "robin";
  	}
}