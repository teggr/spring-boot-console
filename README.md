# spring-boot-console
Demo project for building a console application with spring boot. Further details are in the [robintegg.com blog](http://robintegg.com) 

## Debugging in eclipse

The JLine console does not work well within the eclipse console, especially in windows. In my MAC environment, this seemed to be ok

Try turning off the CRaSH terminal autoconfiguration using

	-Dorg.crsh.console.jline.terminal=none

I've also added a switch for this in the interactive runner to use the synchronous shell. Looks odd, but does seem to work

	String property = System.getProperty(TerminalFactory.JLINE_TERMINAL);
	if (TerminalFactory.NONE.equals(property)) {	
		shell = shellFactory.create(null, false);
	} else {
			shell = shellFactory.create(null);
	}
