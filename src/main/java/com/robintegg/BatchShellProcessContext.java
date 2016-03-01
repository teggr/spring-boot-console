package com.robintegg;

import java.io.IOException;

import org.crsh.shell.ShellProcessContext;
import org.crsh.shell.ShellResponse;
import org.crsh.text.Screenable;
import org.crsh.text.Style;

public class BatchShellProcessContext implements ShellProcessContext {

	@Override
	public boolean takeAlternateBuffer() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean releaseAlternateBuffer() throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getProperty(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String readLine(String msg, boolean echo) throws IOException, InterruptedException, IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 80;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Screenable append(Style style) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Screenable cls() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appendable append(CharSequence csq) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appendable append(CharSequence csq, int start, int end) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Appendable append(char c) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void end(ShellResponse response) {
		// TODO Auto-generated method stub

	}

}
