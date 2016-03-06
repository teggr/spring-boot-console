package com.robintegg;

import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.crsh.console.jline.JLineProcessor;
import org.crsh.console.jline.Terminal;
import org.crsh.console.jline.TerminalFactory;
import org.crsh.console.jline.console.ConsoleReader;
import org.crsh.console.jline.internal.Configuration;
import org.crsh.plugin.PluginLifeCycle;
import org.crsh.shell.Shell;
import org.crsh.shell.ShellFactory;
import org.crsh.shell.impl.command.CRaSHShellFactory;
import org.crsh.util.InterruptHandler;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class InteractiveShellRunner implements CommandLineRunner, InitializingBean, DisposableBean {

	final private PluginLifeCycle crshBootstrapBean;
	private Shell shell;
	private Terminal term;

	@Autowired
	public InteractiveShellRunner(PluginLifeCycle crshBootstrapBean) {
		this.crshBootstrapBean = crshBootstrapBean;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		CRaSHShellFactory shellFactory = (CRaSHShellFactory) crshBootstrapBean.getContext()
				.getPlugin(ShellFactory.class);

		String terminal = System.getProperty("org.crsh.console.jline.terminal");
		if (terminal.equals("none")) {
			shell = shellFactory.create(null, false);
		} else {
			shell = shellFactory.create(null);
		}
	}

	@Override
	public void destroy() throws Exception {
		try {
			if (term != null) {
				term.restore();
			}
		} catch (Exception ignore) {
		}
	}

	@Override
	public void run(String... args) throws Exception {

		if (shell != null) {

			term = TerminalFactory.create();

			//
			String encoding = Configuration.getEncoding();

			// Use AnsiConsole only if term doesnt support Ansi
			PrintStream out;
			PrintStream err;
			boolean ansi;
			if (term.isAnsiSupported()) {
				out = new PrintStream(
						new BufferedOutputStream(term.wrapOutIfNeeded(new FileOutputStream(FileDescriptor.out)), 16384),
						false, encoding);
				err = new PrintStream(
						new BufferedOutputStream(term.wrapOutIfNeeded(new FileOutputStream(FileDescriptor.err)), 16384),
						false, encoding);
				ansi = true;
			} else {
				out = AnsiConsole.out;
				err = AnsiConsole.err;
				ansi = false;
			}

			//
			FileInputStream in = new FileInputStream(FileDescriptor.in);
			ConsoleReader reader = new ConsoleReader(null, in, out, term);

			//
			final JLineProcessor processor = new JLineProcessor(ansi, shell, reader, out);

			//
			InterruptHandler interruptHandler = new InterruptHandler(processor::interrupt);
			interruptHandler.install();

			//
			Thread thread = new Thread(processor);
			thread.setDaemon(true);
			thread.start();

			try {
				processor.closed();
			} catch (Throwable t) {
				t.printStackTrace();
			}

			System.exit(0);

		}

	}

}