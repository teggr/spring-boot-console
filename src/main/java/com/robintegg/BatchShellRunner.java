package com.robintegg;

import java.util.stream.Collectors;

import org.crsh.plugin.PluginLifeCycle;
import org.crsh.shell.Shell;
import org.crsh.shell.ShellFactory;
import org.crsh.shell.ShellProcess;
import org.crsh.shell.impl.command.CRaSHShellFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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