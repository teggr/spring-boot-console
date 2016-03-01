package com.robintegg;

import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.crsh.cli.descriptor.CommandDescriptor;
import org.crsh.cli.impl.Delimiter;
import org.crsh.cli.impl.descriptor.HelpDescriptor;
import org.crsh.cli.impl.invocation.InvocationMatch;
import org.crsh.cli.impl.invocation.InvocationMatcher;
import org.crsh.cli.impl.lang.CommandFactory;
import org.crsh.cli.impl.lang.Instance;
import org.crsh.cli.impl.lang.ObjectCommandDescriptor;
import org.crsh.cli.impl.lang.Util;
import org.crsh.command.CommandContext;
import org.crsh.console.jline.JLineProcessor;
import org.crsh.console.jline.Terminal;
import org.crsh.console.jline.TerminalFactory;
import org.crsh.console.jline.console.ConsoleReader;
import org.crsh.console.jline.internal.Configuration;
import org.crsh.lang.LanguageCommandResolver;
import org.crsh.lang.spi.Language;
import org.crsh.plugin.PluginContext;
import org.crsh.plugin.PluginLifeCycle;
import org.crsh.shell.Shell;
import org.crsh.shell.ShellFactory;
import org.crsh.shell.ShellProcess;
import org.crsh.shell.impl.command.CRaSHShellFactory;
import org.crsh.shell.impl.command.spi.Command;
import org.crsh.shell.impl.command.spi.CommandInvoker;
import org.crsh.shell.impl.command.spi.CommandResolver;
import org.crsh.shell.impl.remoting.ClientAutomaton;
import org.crsh.standalone.CRaSH;
import org.crsh.util.InterruptHandler;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class BatchShellRunner implements ApplicationRunner, InitializingBean {

	final private PluginLifeCycle crshBootstrapBean;
	private Shell shell;

	@Autowired
	public BatchShellRunner(PluginLifeCycle crshBootstrapBean) {
		this.crshBootstrapBean = crshBootstrapBean;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		CRaSHShellFactory shellFactory = (CRaSHShellFactory) crshBootstrapBean.getContext()
				.getPlugin(ShellFactory.class);
		shell = shellFactory.create(null, false);
	}

	@Override
	public void run(ApplicationArguments arg0) throws Exception {

		ShellProcess process = shell.createProcess(arg0.getNonOptionArgs().stream().collect(Collectors.joining(" ")));

		process.execute(new BatchShellProcessContext());

		System.exit(0);

	}

}